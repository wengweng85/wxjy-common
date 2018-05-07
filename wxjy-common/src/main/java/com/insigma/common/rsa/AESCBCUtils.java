package com.insigma.common.rsa;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESCBCUtils {

	public static final String KEY_ALGORITHM = "AES";
	// ����ģʽΪECB�����ģʽΪNoPadding
	public static final String CIPHER_ALGORITHM = "AES/CBC/NoPadding";
	// �ַ���
	public static final String ENCODING = "UTF-8";
	// ����
	public static final String IV_SEED = "1234567812345678";

	/**
	 * AES�����㷨
	 *
	 * @param str ����
	 * @param key ��key
	 * @return
	 */
	public static String encrypt(String str, String key) {
		try {
			if (str == null) {
				System.out.println("AES���ܳ���:KeyΪ��null");
				return null;
			}
			// �ж�Key�Ƿ�Ϊ16λ
			if (key.length() != 16) {
				System.out.println("AES���ܳ���:Key���Ȳ���16λ");
				return null;
			}
			byte[] raw = key.getBytes(ENCODING);
			SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			IvParameterSpec iv = new IvParameterSpec(IV_SEED.getBytes(ENCODING));
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] srawt = str.getBytes(ENCODING);
			int len = srawt.length;
			/* ���㲹�ո��ĳ��� */
			while (len % 16 != 0)
				len++;
			byte[] sraw = new byte[len];
			/* �����ո� */
			for (int i = 0; i < len; ++i) {
				if (i < srawt.length) {
					sraw[i] = srawt[i];
				} else {
					sraw[i] = 32;
				}
			}
			byte[] encrypted = cipher.doFinal(sraw);
			return formatString(new String(Base64.encodeBase64(encrypted), "UTF-8"));
		} catch (Exception ex) {
			System.out.println("AES���ܳ���" + ex.toString());
			return null;
		}
	}

	/**
	 * AES�����㷨
	 *
	 * @param str ����
	 * @param key ��key
	 * @return
	 */
	public static String decrypt(String str, String key) {
		try {
			// �ж�Key�Ƿ���ȷ
			if (key == null) {
				System.out.println("AES���ܳ���:KeyΪ��null");
				return null;
			}
			// �ж�Key�Ƿ�Ϊ16λ
			if (key.length() != 16) {
				System.out.println("AES���ܳ���Key���Ȳ���16λ");
				return null;
			}
			byte[] raw = key.getBytes(ENCODING);
			SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			IvParameterSpec iv = new IvParameterSpec(IV_SEED.getBytes(ENCODING));
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] bytes = Base64.decodeBase64(str.getBytes("UTF-8"));
			bytes = cipher.doFinal(bytes);
			return new String(bytes, ENCODING);
		} catch (Exception ex) {
			System.out.println("AES���ܳ���" + ex.toString());
			return null;
		}
	}

	private static String formatString(String sourceStr) {
		if (sourceStr == null) {
			return null;
		}
		return sourceStr.replaceAll("\\r", "").replaceAll("\\n", "");
	}
}