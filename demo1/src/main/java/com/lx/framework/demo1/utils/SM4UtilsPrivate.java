package com.lx.framework.demo1.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component("sM4UtilsPrivate")
public class SM4UtilsPrivate {

//    private SymmetricCrypto SM4Client = null;

    @Value("${sm4.public.key:698QGB9jn9y0iPbe}")
    private String publicKey;

	private static String staticPublicKey;

	@PostConstruct
	public void init() {
		staticPublicKey = publicKey;
	}

    private static class SM4Holder {
        private static final SymmetricCrypto INSTANCE = SmUtil.sm4(staticPublicKey.getBytes());
    }

    private SymmetricCrypto getSM4Client() {
        return SM4Holder.INSTANCE;
    }

    /**
     * 公钥加密
     *
     * @param data
     * @return
     */
	public String enCode(String data) {
		if (null == data || "".equals(data)) {
			return data;
		}
		try {
			getSM4Client().decryptStr(data);
		} catch (Exception e) {
			data = getSM4Client().encryptHex(data);
		}
		return data;
	}
	
	
	/**
     * 公钥加密
     *
     * @param data
     * @return
     */
	public String enCode(String data,String secret) {
		SymmetricCrypto sM4Client = SmUtil.sm4(secret.getBytes());
		if (null == data || "".equals(data)) {
			return data;
		}
		return sM4Client.encryptHex(data);
	}

    /**
     * 私钥解密
     *
     * @param data
     * @return
     */
    public String deCode(String data) {
    	if (null == data || "".equals(data)) {
			return data;
		}
		try {
			data = getSM4Client().decryptStr(data);
		} catch (Exception e) {
		}
        return data;
    }

    /**
     * 通过证件号获取页面显示的脱敏数据
     *
     * @param credeNo
     * @return
     */
    public String CredeNoDecrypt(String credeNo) {
        StringBuilder stringBuilder = new StringBuilder();
        if (!StringUtils.isEmpty(credeNo)) {
            if (credeNo.length() > 10) {
                stringBuilder.append(credeNo, 0, 3);
                stringBuilder.append(getStar(credeNo.length() - 7));
                stringBuilder.append(credeNo, credeNo.length() - 4, credeNo.length());
            }else {
                stringBuilder.append(credeNo, 0, 1);
                stringBuilder.append(getStar(credeNo.length() - 2));
                stringBuilder.append(credeNo, credeNo.length() - 1, credeNo.length());
            }
        }
        return stringBuilder.toString();
    }

    public StringBuilder getStar(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        if (n > 0) {
            for (int integer = 0; integer < n; integer++) {
                stringBuilder.append("*");
            }
        }
        return stringBuilder;
    }
    
    public String credeNoDecryptForName(String credeNo) {
        StringBuilder stringBuilder = new StringBuilder();
        if (!StringUtils.isEmpty(credeNo)) {
        	stringBuilder.append(credeNo, 0, 1);
        	stringBuilder.append(getStar(credeNo.length() - 1));
        }
        return stringBuilder.toString();
    }
    
    public static void main(String[] args) {
    	
    	String data = "13000000000";
		 SymmetricCrypto sm4 = SmUtil.sm4("698QGB9jn9y0iPbe".getBytes());
//		 SymmetricCrypto sm4 = SmUtil.sm4("7210518927313387".getBytes());
		//公钥加密
		try {
			sm4.decryptStr(data);
		} catch (Exception e) {
			data =sm4.encryptHex(data);
		}
		System.out.println(data);
		
		//私钥解密
		try {
			data = sm4.decryptStr(data);
		} catch (Exception e) {
		}
		
		System.out.println(data);
		System.out.println(sm4.encryptHex("13810351781"));

		System.out.println(sm4.encryptHex(SecureUtil.md5("654321")));
		System.out.println(SecureUtil.md5("654321"));
		System.out.println(System.currentTimeMillis());
    	
		
	}
    
    
    
}
