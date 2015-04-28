package com.self.crypt;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by sh-xiayf on 15-4-28.
 */
public class RSA {

    public static final String CHAR_ENCODING = "UTF-8";
    public static final String AES_ALGORITHM = "AES/ECB/PKCS5Padding";
    public static final String RSA_ALGORITHM = "RSA/ECB/PKCS1Padding";

    /**
     * 加密方法 source�?源数�?
     */
    public static String encrypt(String source, String publicKey)
            throws Exception {
        Key key = getPublicKey(publicKey);
        /** 得到Cipher对象来实现对源数据的RSA加密 */
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] b = source.getBytes();
        /** 执行加密操作 */
        byte[] b1 = cipher.doFinal(b);
        return Base64.encode(b1);
    }

    /**
     * 解密算法 cryptograph:密文
     */
	public static String decrypt(String cryptograph, String privateKey)
			throws Exception {
		Key key = getPrivateKey(privateKey);
		/** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
		Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] b1 = Base64.decode(cryptograph);
		/** 执行解密操作 */
		byte[] b = cipher.doFinal(b1);
		return new String(b);
	}

    /**
     * 得到公钥
     *
     * @param key
     *            密钥字符串（经过base64编码�?
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
                Base64.decode(key));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 得到私钥
     *
     * @param key
     *            密钥字符串（经过base64编码�?
     * @throws Exception
     */
	public static PrivateKey getPrivateKey(String key) throws Exception {
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(
				Base64.decode(key));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

}
