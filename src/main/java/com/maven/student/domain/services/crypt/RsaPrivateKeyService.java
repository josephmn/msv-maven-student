package com.maven.student.domain.services.crypt;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * Service Rsa Private Key Service.
 * This service get private key.
 *
 * @author Joseph Magallanes
 * @since 2026-04-23
 */
@Slf4j
@Service
public class RsaPrivateKeyService {

    @Value("${key.private}")
    private String privateKeyPem;

    public PrivateKey getPrivateKey() {
        //log.info("getPrivateKey: {}",  privateKeyPem);
        try {
            final String privateKeyContent = privateKeyPem
                    .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                    .replace("-----END RSA PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");

            final byte[] keyBytes = Base64.getDecoder().decode(privateKeyContent);

            final PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);

            final KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            return keyFactory.generatePrivate(spec);
        }
        catch (Exception e) {
            throw new RuntimeException("Error loading private key", e);
        }
    }
}
