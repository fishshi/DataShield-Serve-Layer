package com.datashield.util;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * Json Web Token 工具类
 */
public class JwtUtil {
    /**
     * JWT 加密密钥
     */
    private static final String SECRET = "DataShieldJsonWebTokenAuthSecret";

    /**
     * JWT 过期时间
     */
    private static final Integer EXPIRE_TIME = 60 * 60 * 24 * 7;

    /**
     * 生成 JWT Token
     *
     * @param username 用户名
     *
     * @return JWT Token
     */
    public static String generateToken(String username) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        String token = JWT.create()
                .withClaim("username", username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME * 1000))
                .sign(algorithm);
        return token;
    }

    /**
     * 验证并解析 JWT Token
     *
     * @param token JWT Token
     * 
     * @return 用户名, 若验证失败则返回 null
     */
    public static String verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("username").asString();
        } catch (JWTVerificationException e) {
            return null;
        }
    }
}
