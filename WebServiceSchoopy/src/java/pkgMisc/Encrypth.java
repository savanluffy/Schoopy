/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgMisc;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author schueler
 */
public class Encrypth {

    private Encrypth(){
    }
    public static String generateRandomKey(String username,String password) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        SecureRandom secRandom = new SecureRandom();
        keyGen.init(secRandom);
        Key key = keyGen.generateKey();

        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        String msg = username + ":" + password;
        byte[] bytes = cipher.doFinal(msg.getBytes());
        return Base64.getEncoder().encodeToString(bytes);
    }
    
    public static String hashPW(String pw) throws Exception {
        byte[] salt = new byte[16];
        KeySpec spec = new PBEKeySpec(pw.toCharArray(), salt, 65536, 128);
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = f.generateSecret(spec).getEncoded();

        Base64.Encoder enc = Base64.getEncoder();
        return enc.encodeToString(hash);
    }
}
