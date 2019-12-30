package com.lby.walk.model.dto.req;

import lombok.Data;

@Data
public class ClockInDTO {

    private Integer step;

    private String openId;

    private String encryptedData;  //包含步数的加密数据

    private String iv;    //加密算法的初始向量

    private String sessionKey; //登陆后得到的sessionKey
}
