package com.maven.student.domain.services.crypt;

import java.security.PublicKey;
import java.util.Base64;
import javax.crypto.Cipher;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Service Rsa Encrypt Service.
 * This service encrypt values.
 *
 * @author Joseph Magallanes
 * @since 2026-04-24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RsaEncryptService {

    private final RsaPublicKeyService publicKeyService;

    /**
     * Encrypts an encrypted value using RSA encryption.
     *
     * @param encryptedValue the string value to encrypt
     * @return a Mono emitting the encrypted value as a String
     */
    public Mono<String> encrypt(String encryptedValue) {

        return Mono.fromCallable(() -> {
            final PublicKey publicKey = publicKeyService.getPublicKey();
            final Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");

            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            final byte[] encrypted = cipher.doFinal(encryptedValue.getBytes());

            log.info("encrypt: {}", Base64.getEncoder().encodeToString(encrypted));
            return Base64.getEncoder().encodeToString(encrypted);
        }).subscribeOn(
            Schedulers.boundedElastic());
    }
}
