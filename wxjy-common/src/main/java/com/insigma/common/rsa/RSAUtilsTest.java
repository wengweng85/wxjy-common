package com.insigma.common.rsa;

import org.apache.commons.codec.binary.Base64;

/**
 * wengsh
 */
public class RSAUtilsTest {

    public static  void main(String [] a){
        try{
            //1.
            //����֤��
            RSAUtils.initKey();
            //2.
            //Ҫ���ܵ��ַ���
            String inputStr = "{\"aac002\":\"420624198411037915\"}";
            //���ļ��л�ȡ��Կ
            String publickey=RSAUtils.readKey("d:/publicKey.keystore");
            //��������base64
            String encodedata=RSAUtils.encryptByPublicKey(inputStr,publickey);

            //3.����
            //���ļ��л�ȡ��Կ
            String privatekey=RSAUtils.readKey("d:/privateKey.keystore");
            String outputStr=RSAUtils.decryptByPrivateKey(encodedata,privatekey);
            System.out.println("outputstr="+outputStr);

            String server_inputStr = "{\"aac003\":\"420624198411037915\"}";
            //����˼���base64
            String serverencodedata=RSAUtils.encryptByPrivateKey(server_inputStr,privatekey);
            String server_outputStr=RSAUtils.decryptByPublicKey(serverencodedata,publickey);
            System.out.println("server_outputStr="+server_outputStr);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
