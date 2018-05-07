package com.insigma.common.rsa;

import org.apache.commons.codec.binary.Base64;

/**
 * wengsh
 */
public class RSAUtilsTest {

    public static  void main(String [] a){
        try{
            //1.
            //生成证书
            RSAUtils.initKey();
            //2.
            //要加密的字符串
            String inputStr = "{\"aac002\":\"420624198411037915\"}";
            //从文件中获取公钥
            String publickey=RSAUtils.readKey("d:/publicKey.keystore");
            //加密数据base64
            String encodedata=RSAUtils.encryptByPublicKey(inputStr,publickey);

            //3.解密
            //从文件中获取公钥
            String privatekey=RSAUtils.readKey("d:/privateKey.keystore");
            String outputStr=RSAUtils.decryptByPrivateKey(encodedata,privatekey);
            System.out.println("outputstr="+outputStr);

            String server_inputStr = "{\"aac003\":\"420624198411037915\"}";
            //服务端加密base64
            String serverencodedata=RSAUtils.encryptByPrivateKey(server_inputStr,privatekey);
            String server_outputStr=RSAUtils.decryptByPublicKey(serverencodedata,publickey);
            System.out.println("server_outputStr="+server_outputStr);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
