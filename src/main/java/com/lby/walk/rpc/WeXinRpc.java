package com.lby.walk.rpc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "WeXinRpc", url = "${wx.api.url}")
public interface WeXinRpc {

    @GetMapping(value = "/sns/jscode2session", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String getUserInfo(@RequestParam("appid") String appId, @RequestParam("secret") String secret,
            @RequestParam("js_code") String jsCode, @RequestParam("grant_type") String grantType);
}
