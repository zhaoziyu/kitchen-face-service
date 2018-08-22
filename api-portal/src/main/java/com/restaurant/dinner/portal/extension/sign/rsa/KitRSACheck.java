package com.restaurant.dinner.portal.extension.sign.rsa;

import com.restaurant.dinner.portal.extension.sign.SignConstant;
import com.restaurant.dinner.portal.extension.sign.codec.Base64;
import com.restaurant.dinner.portal.extension.sign.util.SignStreamUtil;
import com.restaurant.dinner.portal.extension.sign.util.SignStringUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-08-25
 */
public class KitRSACheck {
    public static boolean rsaCheck(String content, String sign, String publicKey, String charset, String signType) throws Exception {
        // 防止前端传来的Base64编码的字符串加号被替换成了空格
        sign = sign.replace(" ", "+");

        if (SignConstant.SIGN_TYPE_RSA.equals(signType)) {
            return rsaCheckContent(content, sign, publicKey, charset);
        } else if (SignConstant.SIGN_TYPE_RSA2.equals(signType)) {
            return rsa256CheckContent(content, sign, publicKey, charset);
        } else {
            throw new Exception("Sign Type is Not Support : signType=" + signType);
        }

    }

    private static boolean rsa256CheckContent(String content, String sign, String publicKey, String charset) throws Exception {
        try {
            PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));

            Signature signature = Signature.getInstance(SignConstant.SIGN_SHA256RSA_ALGORITHMS);

            signature.initVerify(pubKey);

            if (SignStringUtil.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            return signature.verify(Base64.decodeBase64(sign.getBytes()));
        } catch (Exception e) {
            throw new Exception("RSAcontent = " + content + ",sign=" + sign + ",charset = " + charset, e);
        }
    }

    private static boolean rsaCheckContent(String content, String sign, String publicKey, String charset) throws Exception {
        try {
            PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));

            Signature signature = Signature.getInstance(SignConstant.SIGN_ALGORITHMS);

            signature.initVerify(pubKey);

            if (SignStringUtil.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            return signature.verify(Base64.decodeBase64(sign.getBytes()));
        } catch (Exception e) {
            throw new Exception("RSAcontent = " + content + ",sign=" + sign + ",charset = " + charset, e);
        }
    }

    private static PublicKey getPublicKeyFromX509(String algorithm, InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        StringWriter writer = new StringWriter();
        SignStreamUtil.io(new InputStreamReader(ins), writer);

        byte[] encodedKey = writer.toString().getBytes();

        encodedKey = Base64.decodeBase64(encodedKey);

        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }
}
