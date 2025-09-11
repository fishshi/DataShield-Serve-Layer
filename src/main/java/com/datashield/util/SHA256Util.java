package com.datashield.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import com.datashield.exception.BusinessException;

/**
 * SHA256加密工具类
 */
public class SHA256Util {
    /**
     * 盐值
     */
    private static final String SALT = "DataShieldServerSalt";

    /**
     * SHA256 加密
     *
     * @param input 加密字符串
     *
     * @return 加密后的字符串
     */
    public static String sha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String saltedInput = input + SALT; // 将密码和盐拼接
            byte[] hashedBytes = md.digest(saltedInput.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new BusinessException("sha256加密算法不存在");
        }
    }
}
