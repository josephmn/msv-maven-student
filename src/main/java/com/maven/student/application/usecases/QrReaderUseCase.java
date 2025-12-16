package com.maven.student.application.usecases;

import reactor.core.publisher.Mono;

/**
 * QrReaderUseCase.
 * This interface defines the contract for reading QR codes from images.
 *
 * @author Joseph Magallanes
 * @since 2025-12-15
 */
public interface QrReaderUseCase {

    /**
     * Reads a QR code from the given image bytes.
     *
     * @param imageBytes the image bytes containing the QR code
     * @return a Mono emitting the decoded QR code text
     */
    Mono<String> readQr(byte[] imageBytes);
}
