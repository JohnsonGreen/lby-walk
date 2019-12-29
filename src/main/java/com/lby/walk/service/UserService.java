package com.lby.walk.service;

import com.alibaba.fastjson.JSONObject;
import com.lby.walk.model.po.TUser;

public interface UserService {

    JSONObject getSessionKeyOrOpenId(String code);

    JSONObject getUserInfo(String encryptedData, String sessionKey, String iv);

    TUser findByOpenid(String openId);

    void saveUser(TUser user);
}
