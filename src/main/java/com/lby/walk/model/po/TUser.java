package com.lby.walk.model.po;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class TUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int name;

    private int type;
}
