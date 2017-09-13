package com.insigma.common.rsa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

  
/**
 * 
 * RSA�㷨����/���ܹ�����
 * @author Administrator
 *
 */
public class RSAUtils {  
  
	private static final Logger LOGGER = LoggerFactory.getLogger(RSAUtils.class);

    /** �㷨���� */
    private static final String ALGORITHOM = "RSA";
    /**�������ɵ���Կ�Ե��ļ����ơ� */
    private static final String RSA_PAIR_FILENAME = "/__RSA_PAIR.txt";
    /** ��Կ��С */
    private static final int KEY_SIZE = 1024;
    /** Ĭ�ϵİ�ȫ�����ṩ�� */
    private static final Provider DEFAULT_PROVIDER = new BouncyCastleProvider();

    private static KeyPairGenerator keyPairGen = null;
    private static KeyFactory keyFactory = null;
    /** �������Կ�ԡ� */
    private static KeyPair oneKeyPair = null;

    private static File rsaPairFile = null;

    static {
        try {
            keyPairGen = KeyPairGenerator.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
            keyFactory = KeyFactory.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error(ex.getMessage());
        }
        rsaPairFile = new File(getRSAPairFilePath());
    }

    private RSAUtils() {
    }

    /**
     * ���ɲ�����RSA��Կ�ԡ�
     */
    private static synchronized KeyPair generateKeyPair() {
        try {
            keyPairGen.initialize(KEY_SIZE, new SecureRandom(DateFormatUtils.format(new Date(),"yyyyMMdd").getBytes()));
            oneKeyPair = keyPairGen.generateKeyPair();
            saveKeyPair(oneKeyPair);
            return oneKeyPair;
        } catch (InvalidParameterException ex) {
            LOGGER.error("KeyPairGenerator does not support a key length of " + KEY_SIZE + ".", ex);
        } catch (NullPointerException ex) {
            LOGGER.error("RSAUtils#KEY_PAIR_GEN is null, can not generate KeyPairGenerator instance.",ex);
        }
        return null;
    }

    /**
     * ��������/��ȡ����Կ���ļ���·����
     */
    private static String getRSAPairFilePath() {
        String urlPath = RSAUtils.class.getResource("/").getPath();
        return (new File(urlPath).getParent() + RSA_PAIR_FILENAME);
    }

    /**
     * ����Ҫ�����µ���Կ���ļ����򷵻� {@code true}������ {@code false}��
     */
    private static boolean isCreateKeyPairFile() {
        // �Ƿ񴴽��µ���Կ���ļ�
        boolean createNewKeyPair = false;
        if (!rsaPairFile.exists() || rsaPairFile.isDirectory()) {
            createNewKeyPair = true;
        }
        return createNewKeyPair;
    }

    /**
     * ��ָ����RSA��Կ�����ļ���ʽ���档
     * 
     * @param keyPair Ҫ�������Կ�ԡ�
     */
    private static void saveKeyPair(KeyPair keyPair) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = FileUtils.openOutputStream(rsaPairFile);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(keyPair);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        	IOUtils.closeQuietly(oos);
            IOUtils.closeQuietly(fos);
        }
    }

    /**
     * ����RSA��Կ�ԡ�
     */
    public static KeyPair getKeyPair() {
        // �����ж��Ƿ���Ҫ���������µ���Կ���ļ�
        if (isCreateKeyPairFile()) {
            // ֱ��ǿ��������Կ���ļ��������뻺�档
            return generateKeyPair();
        }
        if (oneKeyPair != null) {
            return oneKeyPair;
        }
        return readKeyPair();
    }
    
    // ͬ�������������Կ��
    private static KeyPair readKeyPair() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = FileUtils.openInputStream(rsaPairFile);
            ois = new ObjectInputStream(fis);
            oneKeyPair = (KeyPair) ois.readObject();
            return oneKeyPair;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            IOUtils.closeQuietly(ois);
            IOUtils.closeQuietly(fis);
        }
        return null;
    }

    /**
     * ���ݸ�����ϵ����ר��ָ������һ��RSAר�õĹ�Կ����
     * 
     * @param modulus ϵ����
     * @param publicExponent ר��ָ����
     * @return RSAר�ù�Կ����
     */
    public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) {
        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(modulus),
                new BigInteger(publicExponent));
        try {
            return (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException ex) {
            LOGGER.error("RSAPublicKeySpec is unavailable.", ex);
        } catch (NullPointerException ex) {
            LOGGER.error("RSAUtils#KEY_FACTORY is null, can not generate KeyFactory instance.", ex);
        }
        return null;
    }

    /**
     * ���ݸ�����ϵ����ר��ָ������һ��RSAר�õ�˽Կ����
     * 
     * @param modulus ϵ����
     * @param privateExponent ר��ָ����
     * @return RSAר��˽Կ����
     */
    public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) {
        RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus),
                new BigInteger(privateExponent));
        try {
            return (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);
        } catch (InvalidKeySpecException ex) {
            LOGGER.error("RSAPrivateKeySpec is unavailable.", ex);
        } catch (NullPointerException ex) {
            LOGGER.error("RSAUtils#KEY_FACTORY is null, can not generate KeyFactory instance.", ex);
        }
        return null;
    }
    
    /**
     * ���ݸ�����16����ϵ����ר��ָ���ַ�������һ��RSAר�õ�˽Կ����
     * 
     * @param modulus ϵ����
     * @param privateExponent ר��ָ����
     * @return RSAר��˽Կ����
     */
    public static RSAPrivateKey getRSAPrivateKey(String hexModulus, String hexPrivateExponent) {
        if(StringUtils.isBlank(hexModulus) || StringUtils.isBlank(hexPrivateExponent)) {
            if(LOGGER.isDebugEnabled()) {
                LOGGER.debug("hexModulus and hexPrivateExponent cannot be empty. RSAPrivateKey value is null to return.");
            }
            return null;
        }
        byte[] modulus = null;
        byte[] privateExponent = null;
        try {
            modulus = Hex.decodeHex(hexModulus.toCharArray());
            privateExponent = Hex.decodeHex(hexPrivateExponent.toCharArray());
        } catch(DecoderException ex) {
            LOGGER.error("hexModulus or hexPrivateExponent value is invalid. return null(RSAPrivateKey).");
        }
        if(modulus != null && privateExponent != null) {
            return generateRSAPrivateKey(modulus, privateExponent);
        }
        return null;
    }
    
    /**
     * ���ݸ�����16����ϵ����ר��ָ���ַ�������һ��RSAר�õĹ�Կ����
     * 
     * @param modulus ϵ����
     * @param publicExponent ר��ָ����
     * @return RSAר�ù�Կ����
     */
    public static RSAPublicKey getRSAPublidKey(String hexModulus, String hexPublicExponent) {
        if(StringUtils.isBlank(hexModulus) || StringUtils.isBlank(hexPublicExponent)) {
            if(LOGGER.isDebugEnabled()) {
                LOGGER.debug("hexModulus and hexPublicExponent cannot be empty. return null(RSAPublicKey).");
            }
            return null;
        }
        byte[] modulus = null;
        byte[] publicExponent = null;
        try {
            modulus = Hex.decodeHex(hexModulus.toCharArray());
            publicExponent = Hex.decodeHex(hexPublicExponent.toCharArray());
        } catch(DecoderException ex) {
            LOGGER.error("hexModulus or hexPublicExponent value is invalid. return null(RSAPublicKey).");
        }
        if(modulus != null && publicExponent != null) {
            return generateRSAPublicKey(modulus, publicExponent);
        }
        return null;
    }

    /**
     * ʹ��ָ���Ĺ�Կ�������ݡ�
     * 
     * @param publicKey �����Ĺ�Կ��
     * @param data Ҫ���ܵ����ݡ�
     * @return ���ܺ�����ݡ�
     */
    public static byte[] encrypt(PublicKey publicKey, byte[] data) throws Exception {
        Cipher ci = Cipher.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
        ci.init(Cipher.ENCRYPT_MODE, publicKey);
        return ci.doFinal(data);
    }

    /**
     * ʹ��ָ����˽Կ�������ݡ�
     * 
     * @param privateKey ������˽Կ��
     * @param data Ҫ���ܵ����ݡ�
     * @return ԭ���ݡ�
     */
    public static byte[] decrypt(PrivateKey privateKey, byte[] data) throws Exception {
        Cipher ci = Cipher.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
        ci.init(Cipher.DECRYPT_MODE, privateKey);
        return ci.doFinal(data);
    }

    /**
     * ʹ�ø����Ĺ�Կ���ܸ������ַ�����
     * <p />
     * �� {@code publicKey} Ϊ {@code null}������ {@code plaintext} Ϊ {@code null} �򷵻� {@code
     * null}��
     * 
     * @param publicKey �����Ĺ�Կ��
     * @param plaintext �ַ�����
     * @return �����ַ��������ġ�
     */
    public static String encryptString(PublicKey publicKey, String plaintext) {
        if (publicKey == null || plaintext == null) {
            return null;
        }
        byte[] data = plaintext.getBytes();
        try {
            byte[] en_data = encrypt(publicKey, data);
            return new String(Hex.encodeHex(en_data));
        } catch (Exception ex) {
            LOGGER.error(ex.getCause().getMessage());
        }
        return null;
    }
    
    /**
     * ʹ��Ĭ�ϵĹ�Կ���ܸ������ַ�����
     * <p />
     * ��{@code plaintext} Ϊ {@code null} �򷵻� {@code null}��
     * 
     * @param plaintext �ַ�����
     * @return �����ַ��������ġ�
     */
    public static String encryptString(String plaintext) {
        if(plaintext == null) {
            return null;
        }
        byte[] data = plaintext.getBytes();
        KeyPair keyPair = getKeyPair();
        try {
            byte[] en_data = encrypt((RSAPublicKey)keyPair.getPublic(), data);
            return new String(Hex.encodeHex(en_data));
        } catch(NullPointerException ex) {
            LOGGER.error("keyPair cannot be null.");
        } catch(Exception ex) {
            LOGGER.error(ex.getCause().getMessage());
        }
        return null;
    }

    /**
     * ʹ�ø�����˽Կ���ܸ������ַ�����
     * <p />
     * ��˽ԿΪ {@code null}������ {@code encrypttext} Ϊ {@code null}����ַ����򷵻� {@code null}��
     * ˽Կ��ƥ��ʱ������ {@code null}��
     * 
     * @param privateKey ������˽Կ��
     * @param encrypttext ���ġ�
     * @return ԭ���ַ�����
     */
    public static String decryptString(PrivateKey privateKey, String encrypttext) {
        if (privateKey == null || StringUtils.isBlank(encrypttext)) {
            return null;
        }
        try {
            byte[] en_data = Hex.decodeHex(encrypttext.toCharArray());
            byte[] data = decrypt(privateKey, en_data);
            return new String(data);
        } catch (Exception ex) {
            LOGGER.error(String.format("\"%s\" Decryption failed. Cause: %s", encrypttext, ex.getCause().getMessage()));
        }
        return null;
    }
    
    /**
     * ʹ��Ĭ�ϵ�˽Կ���ܸ������ַ�����
     * <p />
     * ��{@code encrypttext} Ϊ {@code null}����ַ����򷵻� {@code null}��
     * ˽Կ��ƥ��ʱ������ {@code null}��
     * 
     * @param encrypttext ���ġ�
     * @return ԭ���ַ�����
     */
    public static String decryptString(String encrypttext) {
        if(StringUtils.isBlank(encrypttext)) {
            return null;
        }
        KeyPair keyPair = getKeyPair();
        try {
            byte[] en_data = Hex.decodeHex(encrypttext.toCharArray());
            byte[] data = decrypt((RSAPrivateKey)keyPair.getPrivate(), en_data);
            return new String(data);
        } catch(NullPointerException ex) {
            LOGGER.error("keyPair cannot be null.");
        } catch (Exception ex) {
            LOGGER.error(String.format("\"%s\" Decryption failed. Cause: %s", encrypttext, ex.getMessage()));
        }
        return null;
    }
    
    /**
     * ʹ��Ĭ�ϵ�˽Կ������JS���ܣ�ʹ�ô����ṩ�Ĺ�Կ���ܣ����ַ�����
     * 
     * @param encrypttext ���ġ�
     * @return {@code encrypttext} ��ԭ���ַ�����
     */
    public static String decryptStringByJs(String encrypttext) {
        String text = decryptString(encrypttext);
        if(text == null) {
            return null;
        }
        return text;
    }
    
    /** �����ѳ�ʼ����Ĭ�ϵĹ�Կ��*/
    public static RSAPublicKey getDefaultPublicKey() {
        KeyPair keyPair = getKeyPair();
        if(keyPair != null) {
            return (RSAPublicKey)keyPair.getPublic();
        }
        return null;
    }
    
    /** �����ѳ�ʼ����Ĭ�ϵ�˽Կ��*/
    public static RSAPrivateKey getDefaultPrivateKey() {
        KeyPair keyPair = getKeyPair();
        if(keyPair != null) {
            return (RSAPrivateKey)keyPair.getPrivate();
        }
        return null;
    }
    
    /** ����public key ��*/
    public static PublicKeyMap getPublicKeyMap() {
    	PublicKeyMap publicKeyMap = new PublicKeyMap();
    	RSAPublicKey rsaPublicKey = getDefaultPublicKey();
    	publicKeyMap.setModulus(new String(Hex.encodeHex(rsaPublicKey.getModulus().toByteArray())));
		publicKeyMap.setExponent(new String(Hex.encodeHex(rsaPublicKey.getPublicExponent().toByteArray())));
		return publicKeyMap;
    }
    /**
     * ��publickey ���õ�map��
     * @param map
     */
    @SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static void getPublicKeyMap(Map map) {
    	PublicKeyMap publicKeyMap = new PublicKeyMap();
    	RSAPublicKey rsaPublicKey = getDefaultPublicKey();
    	map.put("publicKeyExponent", new String(Hex.encodeHex(rsaPublicKey.getPublicExponent().toByteArray())));  
		map.put("publicKeyModulus", new String(Hex.encodeHex(rsaPublicKey.getModulus().toByteArray())));
    }
}