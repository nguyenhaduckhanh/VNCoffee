package com.example.vncoffee;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class PasswordUtils {
    private static final String ALGORITHM = "AES";
    private static final byte[] keyValue = "ThisIsASecretKey".getBytes(StandardCharsets.UTF_8); // 16 bytes key

    public static String encrypt(String password) {
        try {
            Key key = generateKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedByteValue = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
            return DatatypeConverter.printBase64Binary(encryptedByteValue);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String encryptedPassword) {
        try {
            Key key = generateKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedValue = DatatypeConverter.parseBase64Binary(encryptedPassword);
            byte[] decryptedByteValue = cipher.doFinal(decryptedValue);
            return new String(decryptedByteValue, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Key generateKey() {
        return new SecretKeySpec(keyValue, ALGORITHM);
    }
}