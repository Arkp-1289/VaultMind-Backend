package com.arkp.VaultMind.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class VaultUtil {
    private static final int ITERATIONS = 200_000;
    private static final int KEY_LENGTH = 256;

    // Generate salt (string)
    public static  String generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // Derive AES key from string password + string salt
    public  static SecretKeySpec deriveAESKey(String masterKey, String saltBase64) throws Exception {
        byte[] saltBytes = Base64.getDecoder().decode(saltBase64);

        KeySpec spec = new PBEKeySpec(masterKey.toCharArray(), saltBytes, ITERATIONS, KEY_LENGTH);
        byte[] keyBytes =
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                        .generateSecret(spec).getEncoded();

        return new SecretKeySpec(keyBytes, "AES");
    }

    // Encrypt (masterKey + salt), returns Base64 string
    public static String encrypt(String plaintext, String masterKey, String salt) throws Exception {
        SecretKeySpec key = deriveAESKey(masterKey, salt);

        byte[] iv = new byte[12];
        new SecureRandom().nextBytes(iv);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(128, iv));

        byte[] encrypted = cipher.doFinal(plaintext.getBytes());

        // return iv + ciphertext in one Base64 string
        byte[] combined = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);

        return Base64.getEncoder().encodeToString(combined);
    }

    // Decrypt
    public static  String decrypt(String encryptedBase64, String masterKey, String salt) throws Exception {
        System.out.println("salt lenL "+salt.length());
        SecretKeySpec key = deriveAESKey(masterKey, salt);
        byte[] decoded = Base64.getDecoder().decode(encryptedBase64);
        byte[] iv = new byte[12];
        byte[] ciphertext = new byte[decoded.length - 12];
        System.arraycopy(decoded, 0, iv, 0, 12);
        System.arraycopy(decoded, 12, ciphertext, 0, ciphertext.length);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(128, iv));
        byte[] decrypted = cipher.doFinal(ciphertext);
        return new String(decrypted);
    }
}
