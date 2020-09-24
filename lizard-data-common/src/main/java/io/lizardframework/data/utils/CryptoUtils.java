package io.lizardframework.data.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Use 3DES Crypto, support ecb and cbc model
 *
 * @author xueqi
 * @date 2020-09-24
 */
public class CryptoUtils {
	private static final String ALGORITHM            = "DESede";
	private static final String CIPHER_ALGORITHM_ECB = "DESede/ECB/PKCS5Padding";
	private static final String CIPHER_ALGORITHM_CBC = "DESede/CBC/PKCS5Padding";
	private static final String CHARSET              = "utf-8";
	private static final byte[] KEYIV                = {'f', 'o', 'a', 'o', 'c', 'u', 'e', 'n'};

	private static SecretKey generateKey(String password) throws Exception {
		byte[]           key        = password.getBytes(CHARSET);
		DESedeKeySpec    spec       = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey        deskey     = keyfactory.generateSecret(spec);

		return deskey;
	}

	/**
	 * 3des ecb model encrypt
	 *
	 * @param password password must be more than 24
	 * @param data     wait encrypt data
	 * @return
	 */
	public static String encryptByECB(String password, String data) {
		try {
			SecretKey deskey = generateKey(password);

			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
			cipher.init(Cipher.ENCRYPT_MODE, deskey);
			byte[] bOut = cipher.doFinal(data.getBytes(CHARSET));
			return new BASE64Encoder().encode(bOut);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 3des ecb model decrypt
	 *
	 * @param password password must be more than 24
	 * @param data     wait decrypt data
	 * @return
	 */
	public static String decryptByECB(String password, String data) {
		try {
			SecretKey deskey = generateKey(password);

			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
			cipher.init(Cipher.DECRYPT_MODE, deskey);
			byte[] bOut = cipher.doFinal(new BASE64Decoder().decodeBuffer(data));
			return new String(bOut, CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 3des cbc model encrypt
	 *
	 * @param password password must be more than 24
	 * @param data     wait encrypt data
	 * @return
	 */
	public static String encryptByCBC(String password, String data) {
		try {
			SecretKey deskey = generateKey(password);

			Cipher          cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
			IvParameterSpec ips    = new IvParameterSpec(KEYIV);
			cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
			byte[] bOut = cipher.doFinal(data.getBytes(CHARSET));
			return new BASE64Encoder().encode(bOut);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 3desc cbc model decrypt
	 *
	 * @param password password must be more than 24
	 * @param data     wait decrypt data
	 * @return
	 */
	public static String decrptyByCBC(String password, String data) {
		try {
			SecretKey deskey = generateKey(password);

			Cipher          cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
			IvParameterSpec ips    = new IvParameterSpec(KEYIV);
			cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
			byte[] bOut = cipher.doFinal(new BASE64Decoder().decodeBuffer(data));
			return new String(bOut, CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
}
