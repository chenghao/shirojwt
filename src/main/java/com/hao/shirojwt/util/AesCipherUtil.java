package com.hao.shirojwt.util;

import com.hao.shirojwt.exception.BDException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;

/**
 * AES加密解密工具类
 */
@Slf4j
@Component
public class AesCipherUtil {

    /**
     * AES密码加密私钥(Base64加密)
     */
    private static String encryptAESKey = PropertiesUtil.getInstance().getProperty("encryptAESKey");
    // private static final byte[] KEY = { 1, 1, 33, 82, -32, -85, -128, -65 };

    public static void main(String[] args) {
        System.out.println(enCrypto("wang123456"));
    }


    private static Map<String, Object> getCipherOrKey() throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        // 实例化支持AES算法的密钥生成器(算法名称命名需按规定，否则抛出异常)
        // KeyGenerator 提供对称密钥生成器的功能，支持各种算法
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        // 将私钥encryptAESKey先Base64解密后转换为byte[]数组按128位初始化
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(Base64ConvertUtil.decode(encryptAESKey).getBytes());
        keygen.init(128, secureRandom);
        // SecretKey 负责保存对称密钥 生成密钥
        SecretKey desKey = keygen.generateKey();
        // 生成Cipher对象,指定其支持的AES算法，Cipher负责完成加密或解密工作
        Cipher c = Cipher.getInstance("AES");

        Map<String, Object> map = new HashMap<>();
        map.put("cipher", c);
        map.put("secretKey", desKey);
        return map;
    }

    /**
     * 加密
     * @param str
     * @return java.lang.String
     */
    public static String enCrypto(String str) {
        try {
            Map<String, Object> map = getCipherOrKey();
            SecretKey desKey = (SecretKey) map.get("secretKey");
            Cipher c = (Cipher) map.get("cipher");

            // 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
            c.init(Cipher.ENCRYPT_MODE, desKey);
            byte[] src = str.getBytes();
            // 该字节数组负责保存加密的结果
            byte[] cipherByte = c.doFinal(src);
            // 先将二进制转换成16进制，再返回Base64加密后的String
            return Base64ConvertUtil.encode(HexConvertUtil.parseByte2HexStr(cipherByte));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            log.error("getInstance()方法异常:" + e.getMessage());
            throw new BDException("getInstance()方法异常:" + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            log.error("Base64加密异常:" + e.getMessage());
            throw new BDException("Base64加密异常:" + e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("初始化Cipher对象异常:" + e.getMessage());
            throw new BDException("初始化Cipher对象异常:" + e.getMessage());
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            log.error("加密异常，密钥有误:" + e.getMessage());
            throw new BDException("加密异常，密钥有误:" + e.getMessage());
        }
    }

    /**
     * 解密
     * @param str
     * @return java.lang.String
     */
    public static String deCrypto(String str) {
        try {
            Map<String, Object> map = getCipherOrKey();
            SecretKey desKey = (SecretKey) map.get("secretKey");
            Cipher c = (Cipher) map.get("cipher");

            // 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示解密模式
            c.init(Cipher.DECRYPT_MODE, desKey);
            // 该字节数组负责保存解密的结果，先对str进行Base64解密，将16进制转换为二进制
            byte[] cipherByte = c.doFinal(HexConvertUtil.parseHexStr2Byte(Base64ConvertUtil.decode(str)));
            return new String(cipherByte);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            log.error("getInstance()方法异常:" + e.getMessage());
            throw new BDException("getInstance()方法异常:" + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            log.error("Base64解密异常:" + e.getMessage());
            throw new BDException("Base64解密异常:" + e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("初始化Cipher对象异常:" + e.getMessage());
            throw new BDException("初始化Cipher对象异常:" + e.getMessage());
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            log.error("解密异常，密钥有误:" + e.getMessage());
            throw new BDException("解密异常，密钥有误:" + e.getMessage());
        }
    }
}
