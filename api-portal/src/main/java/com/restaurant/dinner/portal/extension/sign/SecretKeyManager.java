package com.restaurant.dinner.portal.extension.sign;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 密钥管理
 *
 * TODO 多租户接口具备密钥同步机制，先查询本地缓存，若缓存中无密钥，则访问密钥中心获取对应密钥，并缓存至本地
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/16
 */
@Component
public class SecretKeyManager {

    //-------------------------平台密钥-------------------------
    // 平台私钥（原格式）
    public final static String SERVER_PRIVATE_KEY = "";
    // 平台私钥（PKCS8格式）
    public final static String SERVER_PRIVATE_KEY_PKCS8 = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAMrs2I8xeDi1fVlxulwsUGLb+ru5XiFU7xbR3TWDt4CpLWUOZ21kA1VRP9A97qDQUtFkTa/mLtOFY0RXEUN6o2VphfngwQhFjoNkt6OaGqetItd0OVuDzfTwDUZOgdgb6F0n9plbFGgM08z4CbJDAeqfmcBLr7HeMhPn/CxSLI4rAgMBAAECgYEAhNIhrRgkKHoiYbke0dXvh8WUh+r8wbRmCfmzYKOmKICTReH3sJIV3HIh4pum5Xt1ubh9YPS5EZntL34Nvq025K3uU1foTbZ/ZKKaeGlBRGUmWTQ0fKBJfP07gzl+RLPdVovngLCE2NsC9J9nDSlaDW/uLsr0pBdtmpRVJIAfEwECQQD9fWc8mlowlBjtevwjiXZaUk8jjHV85FbM67xUcwJRkyOO/2aiFHoy9KU8PKXXIP4iwT90JILcFHVcE6VfUTfZAkEAzO9C32D9JvJsjffWhBGKeEbZs83ul6JOLLl0p0MTv6BPmYzYoXoYoUXmsWsx8f6UY6nRYNVJe0c9VFYwT42XowJBAMFbFJJPkpgXf2Q2OXnJ8vM2cZNGIqAfdG5fKoqoJ46d07PR8TGUuRmBL0DhagbM7c0I5yPqbb7+A/82JSCtzRECQQCVNhvFktOfWOErGOmKrU7ZthRqcyUmKJmsvLcv1Yn6exmZ3lAYelKWkdOfGEJ8RPT7/7ggPgtLhBomXr68HYbrAkEAtH+/kCjWQGsQkUXFelkb0TBbbubiU1tAxPPBHzPmDz7GhRnHE/y75JpeSw8sYCb6XVnLufrPNYZN/DlJibWtQA==";
    // 平台公钥
    public final static String SERVER_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDK7NiPMXg4tX1ZcbpcLFBi2/q7uV4hVO8W0d01g7eAqS1lDmdtZANVUT/QPe6g0FLRZE2v5i7ThWNEVxFDeqNlaYX54MEIRY6DZLejmhqnrSLXdDlbg8308A1GToHYG+hdJ/aZWxRoDNPM+AmyQwHqn5nAS6+x3jIT5/wsUiyOKwIDAQAB";

    //-------------------------应用密钥-------------------------
    // 本地RSA密钥缓存
    private static Map<String, String> DEVELOPER_PUBLIC_KEY = new ConcurrentHashMap<>();

    // TODO 演示密钥 实际项目中可去除
    static {
        // 开发者申请的应用标识
        String appId = "0241BDCE04E94DE9B20C9A5455D930B5";

        // 非对称密钥初始化
        // 开发者申请的应用公钥
        String appPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQD2/anmgK6y/x6syoXqM9S0joNQQ8CT/S4z5GEFLlWBZImJl/PHcK3052EjiH1BBxD05IGlRiiwXDVZUwKGb1byv2lT2/KLPSOE1MRbwuEMvqhr+RehaiRYwSK/qJT+Ct0iiifC3flPb09lXMbOjwvT6Ab8vWc5+Qoe7ac+RXCkgQIDAQAB";
        DEVELOPER_PUBLIC_KEY.put(appId, appPublicKey);
        // 开发者的应用私钥（仅为演示使用，系统后台不对应用私钥进行保存）
        String appPrivateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAPb9qeaArrL/HqzKheoz1LSOg1BDwJP9LjPkYQUuVYFkiYmX88dwrfTnYSOIfUEHEPTkgaVGKLBcNVlTAoZvVvK/aVPb8os9I4TUxFvC4Qy+qGv5F6FqJFjBIr+olP4K3SKKJ8Ld+U9vT2Vcxs6PC9PoBvy9Zzn5Ch7tpz5FcKSBAgMBAAECgYEAnI2EV3pRQVu70cI8x4o61IdQbFvFgQgFdRbY+DO6Nt3G39PUzSF64bSXObKV0dXsxYzhMCUcPUz0871N6HBCJzSt9iG+cpGS8X13AIa/uQ9/hFMn0BPyDOvdsL+/kv7Ahq2zHAMTmbJySMO7WCgMzd5O90KPFoJAGNLVXAtz+sECQQD83/bKI68XiepErLMzVIuOVt/WzvrcGOt6z0v1zthRHKdt6+nuhM/jNV1sDeteimgdT77j65qUcjcNMcrxDUUdAkEA+gsViMtoKgDSsMbKhNlLIlAxU/qvNXXwLx0E1Y3zcq+o/yJ4lgYbR3+MZuw8bfqkfparBOdERlrzH8xaG1IztQJAJeECncr0mmkNT5YzDbhXY03+H7ZHe5q8A1xz+3EtlBDfv6Z8Fz+LyHQg92OqYzIGYIWmiYusTxpAxtgzlyIuvQJBAO3AGlK+7iV6MNuruacGIh3XWH/8jhpsMNvrYMxaNBBpnGwz76re1ZNvYSYAHBmKyFwhkS2RZObs1d33ZfoyeD0CQQDzOP5DW/4EvbWc65gEzOnzVc14/7p6MRSRR/lfUYiU1zY1RTgzKylEFee+z/Jqd0IOOpP5dkFdLxHMbrgPZ63K";

    }

    /**
     * 获取指定应用的应用公钥
     *
     * @param appId 开发者申请的应用标识
     * @return 开发者申请的应用公钥
     */
    public static String getAppPublicKey(String appId) {
        return DEVELOPER_PUBLIC_KEY.get(appId);
    }
}
