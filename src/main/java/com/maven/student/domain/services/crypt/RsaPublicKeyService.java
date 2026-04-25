package com.maven.student.domain.services.crypt;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * Service Rsa Public Key Service.
 * This service get public key.
 *
 * @author Joseph Magallanes
 * @since 2026-04-24
 */
@Slf4j
@Service
public class RsaPublicKeyService {

    @Value("${key.public}")
    private String publicKeyPem;

    public PublicKey getPublicKey() {
        //log.info("getPublicKey: {}",  publicKeyPem);
        try {
            final String publicKeyContent = publicKeyPem
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");

            final byte[] keyBytes = Base64.getDecoder().decode(publicKeyContent);

            final X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);

            final KeyFactory factory = KeyFactory.getInstance("RSA");

            return factory.generatePublic(spec);
        }
        catch (Exception e) {
            throw new RuntimeException("Error loading public key", e);
        }
    }
}
