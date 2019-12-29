package com.lby.walk.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lby.walk.rpc.WeXinRpc;
import com.lby.walk.service.UserService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    @Value("${wx.appId}")
    private String APP_ID;

    @Value("${wx.appSecret}")
    private String APP_SECRET;

    @Value("${wx.grantType}")
    private String grantType;

    @Resource
    private WeXinRpc weXinRpc;

    @Override
    public JSONObject getSessionKeyOrOpenId(String code) {

        ////微信端登录code
        //String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid="
        //        + APP_ID
        //        + "&secret="
        //        + APP_SECRET
        //        + "&js_code="
        //        + code
        //        + "&grant_type=authorization_code";
        //发送post请求读取调用微信接口获取openid用户唯一标识
        JSONObject jsonObject = JSON.parseObject(weXinRpc.getUserInfo(APP_ID,APP_SECRET,code,grantType));
        return jsonObject;
    }

    @Override
    public JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) {
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSON.parseObject(result);
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            log.info("e:", e);
        }
        return null;
    }
}
