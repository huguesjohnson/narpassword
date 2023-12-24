/* https://github.com/huguesjohnson/narpassword/blob/main/LICENSE */

package com.huguesjohnson.narpas;

import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public abstract class StringEncryptDecrypt{
	private final static String salt="vQ82Nk0JaD5fKc7I1wo6O9jVHe3iR4A5lFp3Bn41Ub6Sdm2WY0gZ7hqT8Cy9tG17ErsL0z5M8xPuX9X4mAc32rFj6YQ4d3aJn5D7iUKx0bV6k1BtW9fN2Cl8I0eLp4Ho";
    private final static String factoryAlgorithm="PBKDF2WithHmacSHA256";
    private final static String keyAlgorithm="AES";
    private final static String transformation="AES/CBC/PKCS5Padding";
    private final static int iterationCount=65536;
    private final static int keyLength=256;
	private final static byte[] initializationVector={91,-57,68,92,-11,1,-117,66,46,66,-117,39,-74,-71,117,45};
    private final static IvParameterSpec ivParameterSpec=new IvParameterSpec(initializationVector);

	public final static String encrypt(String passphrase,String stringToEncrypt) throws Exception{
        SecretKeyFactory factory=SecretKeyFactory.getInstance(factoryAlgorithm);
        KeySpec keySpec=new PBEKeySpec(passphrase.toCharArray(),salt.getBytes(),iterationCount,keyLength);
        SecretKeySpec secretKey=new SecretKeySpec(factory.generateSecret(keySpec).getEncoded(),keyAlgorithm);
        Cipher cipher=Cipher.getInstance(transformation);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey,ivParameterSpec);
        return(Base64.getEncoder().encodeToString(cipher.doFinal(stringToEncrypt.getBytes("UTF-8"))));
	}
	
	public final static String decryptString(String passphrase,String encryptedString) throws Exception{
        SecretKeyFactory factory=SecretKeyFactory.getInstance(factoryAlgorithm);
        KeySpec spec=new PBEKeySpec(passphrase.toCharArray(),salt.getBytes(),iterationCount,keyLength);
        SecretKeySpec secretKey=new SecretKeySpec(factory.generateSecret(spec).getEncoded(),keyAlgorithm);
        Cipher cipher=Cipher.getInstance(transformation);
        cipher.init(Cipher.DECRYPT_MODE,secretKey,ivParameterSpec);
        return(new String(cipher.doFinal(Base64.getDecoder().decode(encryptedString))));
	}
	
}