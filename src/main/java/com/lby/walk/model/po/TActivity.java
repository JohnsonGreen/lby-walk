package com.lby.walk.model.po;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class TActivity implements Comparable<TActivity>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Date start;

    private Date end;

    private Integer userId;

    private Integer upperBound;

    private Integer lowerBound;

    private Integer rankRule;

    private Integer first;

    private Integer second;

    private Integer third;

    private Integer encourage;

    private Integer status;

    @Override
    public int compareTo(TActivity o) {
        return this.id < o.getId()? -1:0 ;
    }
}
