package com.lby.walk.model.po;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class TUserActivity implements Comparable<TUserActivity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer activityId;

    private Integer step;

    private String openid;

    private Date ctime;

    private Integer status;

    @Override
    public int compareTo(TUserActivity o) {
        return id < o.id ? -1 : 0;
    }
}
