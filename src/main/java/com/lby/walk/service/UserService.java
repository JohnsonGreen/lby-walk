package com.lby.walk.service;

import com.alibaba.fastjson.JSONObject;

public interface UserService {

    JSONObject getSessionKeyOrOpenId(String code);

    JSONObject getUserInfo(String encryptedData, String sessionKey, String iv);
}
