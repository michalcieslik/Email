package Comunication;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Security {
    private static final String keyString = "541043e02727a9ebe720b953f95c7f36";
    private static final String initialVectorString = "66fd534288ba3bd7";
    private static final SecretKey secretKey = new SecretKeySpec(keyString.getBytes(),"AES");
    private static final IvParameterSpec initialVector = new IvParameterSpec(initialVectorString.getBytes());

    public static String encrypt(String message) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, initialVector);
        byte[] encryptedMessageBytes = cipher.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(encryptedMessageBytes);
    }

    public static String decrypt(String encryptedMessage) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, initialVector);
        byte[] encryptedMessageBytes = Base64.getDecoder().decode(encryptedMessage);
        byte[] decryptedMessageBytes = cipher.doFinal(encryptedMessageBytes);
        return new String(decryptedMessageBytes);
    }

    public static String hashPassword(String password)  {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(password.getBytes());
            return Base64.getEncoder().encodeToString(messageDigest.digest());
        }catch (NoSuchAlgorithmException e){
            return null;
        }

    }
    public static void main(String[] args) throws Exception {
        String a = "";
        System.out.println(encrypt(a));
        System.out.println(hashPassword(a));
    }
}
