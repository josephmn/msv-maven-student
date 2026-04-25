package com.maven.student.domain.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.maven.student.application.usecases.QrReaderUseCase;
import reactor.core.publisher.Mono;

/**
 * QrReaderService.
 * This service implements the QrReaderUseCase to read QR codes from images.
 *
 * @author Joseph Magallanes
 * @since 2025-12-15
 */
@Service
public class QrReaderServiceImpl implements QrReaderUseCase {

    @Override
    public Mono<String> readQr(byte[] imageBytes) {
        return Mono.fromCallable(() -> decode(imageBytes));
    }

    private String decode(byte[] imageBytes) throws Exception {
        final BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));

        if (image == null) {
            throw new IllegalArgumentException("Imagen inválida");
        }

        final LuminanceSource source = new BufferedImageLuminanceSource(image);
        final BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        final Result result = new MultiFormatReader().decode(bitmap);
        return result.getText();
    }
}
