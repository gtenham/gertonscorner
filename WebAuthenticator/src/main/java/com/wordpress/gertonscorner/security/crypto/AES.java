/**
 * 
 */
package com.wordpress.gertonscorner.security.crypto;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * @author Gerton
 *
 */
public class AES {

	private static final String ALGORITHM = "AES";
	private static final int BITS128 = 16;
	private static final int BITS192 = 24;
	private static final int BITS256 = 32;
	
	/**
	 * Strong (128 bits) encryption of message using first 16 characters from key.
	 * 
	 * @param key The encryption key (use 256bits hash!)
	 * @param message The message to encrypt
	 * @return base64 encoded string
	 */
	public static String encrypt128(String key, String message) {
		return Base64.encodeBase64String(encrypt(key, message, BITS128));
	}
	
	/**
	 * Unlimited (192 bits) encryption of message using password using first 24 characters from key.
	 * <br/><br/>
	 * Due to import-control restrictions imposed by some countries, 
	 * the jurisdiction policy files shipped with the Java 2 SDK, only 
	 * permit strong cryptography to be used. An unlimited strength version 
	 * of these files (that is, with no restrictions on cryptographic strength) 
	 * is available for download at http://www.oracle.com/technetwork/java/javase/downloads/index.html.
	 * 
	 * @param key The encryption key (use 256bits hash!)
	 * @param message The message to encrypt
	 * @return base64 encoded string
	 */
	public static String encrypt192(String key, String message) {
		return Base64.encodeBase64String(encrypt(key, message, BITS192));
	}
	
	/**
	 * Unlimited (256 bits) encryption of message using password using first 32 characters from key.
	 * <br/><br/>
	 * Due to import-control restrictions imposed by some countries, 
	 * the jurisdiction policy files shipped with the Java 2 SDK, only 
	 * permit strong cryptography to be used. An unlimited strength version 
	 * of these files (that is, with no restrictions on cryptographic strength) 
	 * is available for download at http://www.oracle.com/technetwork/java/javase/downloads/index.html.
	 * 
	 * @param key The encryption key (use 256bits hash!)
	 * @param message The message to encrypt
	 * @return base64 encoded string
	 */
	public static String encrypt256(String key, String message) {
		return Base64.encodeBase64String(encrypt(key, message, BITS256));
	}
	
	/**
	 * Decryption of strong (128bits) encrypted string using first 16 characters from key.
	 * 
	 * @param key The encryption key (use 256bits hash!)
	 * @param message The message to decrypt
	 * @return decrypted string
	 */
	public static String decrypt128(String key, String message) {
		byte[] msg = message.getBytes();
		if (Base64.isBase64(message)) {
			msg = Base64.decodeBase64(message);
		}
		return new String(decrypt(key, msg, BITS128));
	}
	
	/**
	 * Decryption of unlimited (192bits) encrypted string using first 24 characters from key.
	 * <br/><br/>
	 * Due to import-control restrictions imposed by some countries, 
	 * the jurisdiction policy files shipped with the Java 2 SDK, only 
	 * permit strong cryptography to be used. An unlimited strength version 
	 * of these files (that is, with no restrictions on cryptographic strength) 
	 * is available for download at http://www.oracle.com/technetwork/java/javase/downloads/index.html.
	 * 
	 * @param key The encryption key (use 256bits hash!)
	 * @param message The message to decrypt
	 * @return decrypted string
	 */
	public static String decrypt192(String key, String message) {
		byte[] msg = message.getBytes();
		if (Base64.isBase64(message)) {
			msg = Base64.decodeBase64(message);
		}
		return new String(decrypt(key, msg, BITS192));
	}
	
	/**
	 * Decryption of unlimited (256bits) encrypted string using first 32 characters from key.
	 * <br/><br/>
	 * Due to import-control restrictions imposed by some countries, 
	 * the jurisdiction policy files shipped with the Java 2 SDK, only 
	 * permit strong cryptography to be used. An unlimited strength version 
	 * of these files (that is, with no restrictions on cryptographic strength) 
	 * is available for download at http://www.oracle.com/technetwork/java/javase/downloads/index.html.
	 * 
	 * @param key The encryption key (use 256bits hash!)
	 * @param message The message to decrypt
	 * @return decrypted string
	 */
	public static String decrypt256(String key, String message) {
		byte[] msg = message.getBytes();
		if (Base64.isBase64(message)) {
			msg = Base64.decodeBase64(message);
		}
		return new String(decrypt(key, msg, BITS256));
	}
	
	/**
	 * Private encryption method
	 * 
	 * @param keystring
	 * @param message
	 * @param bits
	 * @return
	 */
	private static byte[] encrypt(String keystring, String message, int bits) {
		byte[] encValue = null;
		try {
			Key key = generateKey(keystring, bits);
			Cipher c = Cipher.getInstance(ALGORITHM);
			c.init(Cipher.ENCRYPT_MODE, key);
			encValue = c.doFinal(message.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encValue;
	}
	
	/**
	 * Private decryption method
	 * 
	 * @param keystring
	 * @param message
	 * @param bits
	 * @return
	 */
	private static byte[] decrypt(String keystring, byte[] message, int bits) {
		byte[] decValue = null;
		try {
			Key key = generateKey(keystring, bits);
			Cipher c = Cipher.getInstance(ALGORITHM);
			c.init(Cipher.DECRYPT_MODE, key);
			decValue = c.doFinal(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decValue;
	}
	
	/**
	 * Generate a proper key used for (de)cipher the message
	 * 
	 * @param  String keystring Containing the keystring used for (de)cipher 
	 * 		   					eg. use a 256 hash for convenience.
	 * 							16/24/32 characters for 128/192/256 bits cipher
	 * @param  int bits Number of characters used in keystring
	 * @return Key security key
	 * @throws UnsupportedEncodingException
	 */
	private static Key generateKey(String keystring, int bits) throws UnsupportedEncodingException {
		byte[] keyValue = keystring.substring(0, bits).getBytes("UTF-8");
		Key key = new SecretKeySpec(keyValue, ALGORITHM);
		
		return key;
	}
}
