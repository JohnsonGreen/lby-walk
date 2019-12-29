package com.api.controller.wx;

import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.codehaus.xfire.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.api.entity.User;
import com.api.service.UserService;
import com.api.utils.Constant;
import com.api.utils.HttpSenderUtil;
import com.api.utils.Result;
import com.api.utils.StringUtils;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/api")
@RestController
@Slf4j
public class MiniProgramController {

    @Autowired
    private UserService userService;



    @RequestMapping("/login")
    @ResponseBody
    public Result<Map<String,Object>> doLogin(Model model,
            @RequestParam(value = "code",required = false) String code,
            @RequestParam(value = "rawData",required = false) String rawData,
            @RequestParam(value = "signature",required = false) String signature,
            @RequestParam(value = "encrypteData",required = false) String encrypteData,
            @RequestParam(value = "iv",required = false) String iv,
            @RequestParam(value = "scene",required = false) String scene){

        //System.out.println("用户非敏感信息"+rawData);
        JSONObject rawDataJson = JSON.parseObject( rawData );
        //System.out.println("签名"+signature);
        JSONObject SessionKeyOpenId = getSessionKeyOrOpenId( code );
        //System.out.println("SessionAndopenId="+SessionKeyOpenId);
        String openId = SessionKeyOpenId.getString("openid" );
        String sessionKey = SessionKeyOpenId.getString( "session_key" );
        //System.out.println("openid="+openId+",session_key="+sessionKey);

        Map<String,Object> mapUser = userService.findByOpenid(openId);
        User user = new User();
        if(mapUser==null){
            String nickName = rawDataJson.getString( "nickName" );
            String avatarUrl = rawDataJson.getString( "avatarUrl" );
            String gender  = rawDataJson.getString( "gender" );
            String city = rawDataJson.getString( "city" );
            String country = rawDataJson.getString( "country" );
            String province = rawDataJson.getString( "province" );


        }else {
            System.out.println("用户已存在");
            user.setuId(mapUser.get("U_ID").toString());
            user.setIsSys(mapUser.get("IS_SYS").toString());
        }

        JSONObject userInfo = getUserInfo( encrypteData, sessionKey, iv );
        userInfo.put("uId", user.getuId());
        userInfo.put("isSys", user.getIsSys());
        System.out.println("根据解密算法获取的userInfo="+userInfo);
        return Result.success(userInfo);
    }



}