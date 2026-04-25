package com.maven.student.presentation;

import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import com.google.zxing.NotFoundException;
import com.maven.student.application.usecases.QrReaderUseCase;
import com.openapi.generate.api.QrApi;
import com.openapi.generate.model.QrReadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * QrController.
 * This controller handles QR code reading requests.
 *
 * @author Joseph Magallanes
 * @since 2025-12-15
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class QrController implements QrApi {

    private final QrReaderUseCase qrReaderUseCase;

    @Override
    public Mono<ResponseEntity<QrReadResponse>> readQr(Flux<Part> file, ServerWebExchange exchange) {
        return file
            .ofType(FilePart.class)
            .next()
            .switchIfEmpty(Mono.error(new IllegalArgumentException("File not sent")))
            .flatMap(filePart -> {
                log.info("📦 File received");
                log.info("➡ filename: {}", filePart.filename());
                log.info("➡ name: {}", filePart.name());
                log.info("➡ headers: {}", filePart.headers());

                return DataBufferUtils.join(filePart.content())
                    .flatMap(buffer -> {
                        final byte[] bytes = new byte[buffer.readableByteCount()];
                        buffer.read(bytes);
                        DataBufferUtils.release(buffer);

                        log.info("➡ file size (bytes): {}", bytes.length);

                        return qrReaderUseCase.readQr(bytes)
                            .map(text -> ResponseEntity.ok(new QrReadResponse().qrText(text)))
                            .onErrorResume(NotFoundException.class, ex ->
                                Mono.just(ResponseEntity
                                    .status(HttpStatus.BAD_REQUEST)
                                    .body(new QrReadResponse())))
                            .onErrorResume(Exception.class, ex ->
                                Mono.just(ResponseEntity
                                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .build())
                            );
                    });
            });
    }
}
