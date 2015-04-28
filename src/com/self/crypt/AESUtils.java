package com.self.crypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * Created by sh-xiayf on 15-4-28.
 */
public class AESUtils {
    public static String encryptAndHex(String content,String passwd){
        return parseByte2HexStr(encrypt(content, passwd));
    }

    public static String decryptAnd2String(String content,String passwd){
        return new String(decrypt(parseHexStr2Byte(content), passwd));
    }

    public static SecretKeySpec setSecretKey(String password) throws Exception {
        SecretKeySpec key = null;
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        sr.setSeed(password.getBytes());
        kgen.init(128, sr);
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        key = new SecretKeySpec(enCodeFormat, "AES");

        return key;
    }

    public static byte[] encrypt(String content,String passwd){
        try {
            Cipher cipher = Cipher.getInstance("AES");
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, setSecretKey(passwd));
            byte[] result = cipher.doFinal(byteContent);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decrypt(byte[] content,String passwd){
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, setSecretKey(passwd));
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String parseByte2HexStr(byte[] buf){
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<buf.length;i++){
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if(hex.length() == 1){
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] parseHexStr2Byte(String hexStr){
        if(hexStr.length() < 1){
            return null;
        }
        byte[] result = new byte[hexStr.length()/2];
        for (int i=0;i<hexStr.length()/2;i++){
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1),16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte)(high*16+low);
        }
        return result;
    }
}
