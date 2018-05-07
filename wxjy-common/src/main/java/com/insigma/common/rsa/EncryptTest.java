package com.insigma.common.rsa;

import java.util.HashMap;
import java.util.Map;

public class EncryptTest {

	public static void main(String[] args) {
		/**
		 * 数据加密
		 */
		//aes密钥-16位随机
		String aes_key = "JAL8eZQINC785YHF";
		//rsa公钥
		String default_rsa_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDFn1qxDw9ZS0P0uu5/ieoBHDstWzc6eGJBarKNjh5Q3waZPcgdq0muPEMVtBT/dsO5l4YZLsjRETbyWNKoFlBHthjGJP4X8xuNLsL6punTI3vHr6Yl8hYcSis7g2ergO/Hg3GTm5WyN2Vi3rJbCBS0tR7Bb6/eLs8kTrpECgHngwIDAQAB";

		String plantText="{'idCard':'330382199405051146','name':'张大大','dateTime':'2018-01-22 14:22:45'}";

		String aes_str = AESCBCUtils.encrypt(plantText, aes_key); //aes密文

		try{

		String rsa_str = RSAUtils.encryptByPublicKey(aes_key, default_rsa_public_key); //rsa密文--公钥加密

		//1 请求参数map
		Map<String,String> map = new HashMap<String,String>();

		map.put("aes", aes_str);
		map.put("rsa", rsa_str);

		String sign = SignUtils.createSign(map);
		StringBuffer sb = new StringBuffer();

		sb.append(aes_str).append(RSAUtils.SEPARATOR).append(rsa_str);
		System.out.println("数据密文为：" + sb.toString());
		System.out.println("数据签名为：" + sign);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
