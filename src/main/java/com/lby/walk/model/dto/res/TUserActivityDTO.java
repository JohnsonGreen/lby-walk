package com.lby.walk.model.dto.res;

import lombok.Data;

import java.util.Date;

@Data
public class TUserActivityDTO {

    private Integer id;

    private Integer activityId;

    private Integer step;

    private String openid;

    private Date ctime;

    private Integer status;

    private String ctimeString;
}
