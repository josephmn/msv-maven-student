package com.maven.student.domain.services.crypt;

import java.security.PrivateKey;
import java.util.Base64;
import javax.crypto.Cipher;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Service Rsa Decrypt Service.
 * This service decrypt values.
 *
 * @author Joseph Magallanes
 * @since 2026-04-23
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RsaDecryptService {

    private final RsaPrivateKeyService privateKeyService;

    /**
     * Decrypts an encrypted value using RSA decryption.
     *
     * @param decryptedValue the string value to decrypt
     * @return a Mono emitting the decrypted value as a String
     */
    public Mono<String> decrypt(String decryptedValue) {

        return Mono.fromCallable(() -> {
            final PrivateKey privateKey = privateKeyService.getPrivateKey();
            final Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");

            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            final byte[] decoded = Base64.getDecoder().decode(decryptedValue);

            final byte[] decrypted = cipher.doFinal(decoded);

            log.info("decrypt: {}", new String(decrypted));
            return new String(decrypted);
        }).subscribeOn(
            Schedulers.boundedElastic());
    }
}
