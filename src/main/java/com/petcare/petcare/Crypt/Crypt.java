package com.petcare.petcare.Crypt;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Crypt {
    private final static String chave = "SHA-256-ENCRYPT-NODE";

    public static String encrypt(String text) throws Exception {
        String string = "";
        
        try {
            SecretKeySpec skeyspec = new SecretKeySpec(chave.getBytes(), "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
            byte[] encrypted = cipher.doFinal(text.getBytes());
            string = new String(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }

        return string;
    }

    public static String decrypt(String text) throws Exception {
        String string = "";

        try {
            SecretKeySpec skeyspec = new SecretKeySpec(chave.getBytes(), "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, skeyspec);
            byte[] decrypted = cipher.doFinal(text.getBytes());
            string = new String(decrypted);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }

        return string;
    }
}
