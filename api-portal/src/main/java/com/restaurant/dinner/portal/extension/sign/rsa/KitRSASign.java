package com.restaurant.dinner.portal.extension.sign.rsa;

import com.restaurant.dinner.portal.extension.sign.SignConstant;
import com.restaurant.dinner.portal.extension.sign.codec.Base64;
import com.restaurant.dinner.portal.extension.sign.util.SignStreamUtil;
import com.restaurant.dinner.portal.extension.sign.util.SignStringUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-08-25
 */
public class KitRSASign {
    /**
     * rsa内容签名
     *
     * @param content
     * @param privateKey
     * @param charset
     * @return
     */
    public static String rsaSign(String content, String privateKey, String charset, String signType) throws Exception {

        if (SignConstant.SIGN_TYPE_RSA.equals(signType)) {
            return rsaSign(content, privateKey, charset);
        } else if (SignConstant.SIGN_TYPE_RSA2.equals(signType)) {
            return rsa256Sign(content, privateKey, charset);
        } else {
            throw new Exception("Sign Type is Not Support : signType=" + signType);
        }

    }

    /**
     * sha256WithRsa 加签
     *
     * @param content
     * @param privateKey
     * @param charset
     * @return
     * @throws Exception
     */
    private static String rsa256Sign(String content, String privateKey, String charset) throws Exception {

        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8(SignConstant.SIGN_TYPE_RSA, new ByteArrayInputStream(privateKey.getBytes()));

            Signature signature = Signature.getInstance(SignConstant.SIGN_SHA256RSA_ALGORITHMS);

            signature.initSign(priKey);

            if (SignStringUtil.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            byte[] signed = signature.sign();

            return new String(Base64.encodeBase64(signed));
        } catch (Exception e) {
            throw new Exception("RSAcontent = " + content + "; charset = " + charset, e);
        }

    }

    /**
     * sha1WithRsa 加签
     *
     * @param content
     * @param privateKey
     * @param charset
     * @return
     * @throws Exception
     */
    private static String rsaSign(String content, String privateKey, String charset) throws Exception {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8(SignConstant.SIGN_TYPE_RSA, new ByteArrayInputStream(privateKey.getBytes()));

            Signature signature = Signature.getInstance(SignConstant.SIGN_ALGORITHMS);

            signature.initSign(priKey);

            if (SignStringUtil.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            byte[] signed = signature.sign();

            return new String(Base64.encodeBase64(signed));
        } catch (InvalidKeySpecException ie) {
            throw new Exception("RSA私钥格式不正确，请检查是否正确配置了PKCS8格式的私钥", ie);
        } catch (Exception e) {
            throw new Exception("RSAcontent = " + content + "; charset = " + charset, e);
        }
    }

    private static PrivateKey getPrivateKeyFromPKCS8(String algorithm, InputStream ins) throws Exception {
        if (ins == null || SignStringUtil.isEmpty(algorithm)) {
            return null;
        }

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        byte[] encodedKey = SignStreamUtil.readText(ins).getBytes();

        encodedKey = Base64.decodeBase64(encodedKey);

        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
    }
}
