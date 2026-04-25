package com.maven.student.presentation;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import com.maven.student.domain.services.crypt.RsaDecryptService;
import com.maven.student.domain.services.crypt.RsaEncryptService;
import com.openapi.generate.api.AsymmetricKeyApi;
import com.openapi.generate.model.DecryptRequest;
import com.openapi.generate.model.DecryptResponse;
import com.openapi.generate.model.EncryptRequest;
import com.openapi.generate.model.EncryptResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * AsymmetricController.
 * This controller handles Asymmetric values request.
 *
 * @author Joseph Magallanes
 * @since 2026-04-23
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AsymmetricController implements AsymmetricKeyApi {

    private final RsaDecryptService decryptService;
    private final RsaEncryptService encryptService;

    @Override
    public Mono<ResponseEntity<EncryptResponse>> encryptValue(
        Mono<EncryptRequest> encryptRequest, ServerWebExchange exchange) {
        return encryptRequest.flatMap(dto ->
            this.encryptService.encrypt(dto.getText())
                .map(responseDto -> ResponseEntity
                    .created(URI.create("/api/v1/encrypt-password"))
                    .body(new EncryptResponse().text(responseDto)))
                .defaultIfEmpty(ResponseEntity.notFound().build())
        );
    }

    @Override
    public Mono<ResponseEntity<DecryptResponse>> decryptValue(
        Mono<DecryptRequest> decryptRequest, ServerWebExchange exchange) {
        return decryptRequest.flatMap(dto ->
            this.decryptService.decrypt(dto.getText())
                .map(responseDto -> ResponseEntity
                    .created(URI.create("/api/v1/decrypt-password"))
                    .body(new DecryptResponse().text(responseDto)))
                .defaultIfEmpty(ResponseEntity.notFound().build())
        );
    }
}
