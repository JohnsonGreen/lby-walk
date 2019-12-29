package com.lby.walk.controller.mini;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lby.walk.model.enums.UserTypeEnum;
import com.lby.walk.model.po.TUser;
import com.lby.walk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/v1/api")
@RestController
@Slf4j
public class MiniProgramController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> doLogin(@RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "rawData", required = false) String rawData,
            @RequestParam(value = "signature", required = false) String signature,
            @RequestParam(value = "encrypteData", required = false) String encrypteData,
            @RequestParam(value = "iv", required = false) String iv,
            @RequestParam(value = "scene", required = false) String scene) {

        //System.out.println("用户非敏感信息"+rawData);
        JSONObject rawDataJson = JSON.parseObject(rawData);
        //System.out.println("签名"+signature);
        JSONObject sessionKeyOpenId = userService.getSessionKeyOrOpenId(code);
        //System.out.println("SessionAndopenId="+SessionKeyOpenId);
        String openId = sessionKeyOpenId.getString("openid");
        String sessionKey = sessionKeyOpenId.getString("session_key");
        //System.out.println("openid="+openId+",session_key="+sessionKey);

        TUser ouser = userService.findByOpenid(openId);
        TUser user = new TUser();
        if (ouser == null) {
            user.setName(rawDataJson.getString("nickName"));
            user.setType(UserTypeEnum.NORMAL.getCode());
            user.setOpenid(openId);
            userService.saveUser(user);
        }

        JSONObject userInfo = userService.getUserInfo(encrypteData, sessionKey, iv);
        System.out.println("根据解密算法获取的userInfo=" + userInfo);
        return userInfo;
    }
}