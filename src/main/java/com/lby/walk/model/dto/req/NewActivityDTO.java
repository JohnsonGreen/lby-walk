package com.lby.walk.model.dto.req;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class NewActivityDTO {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end;

    private String name;

    private Integer userId;

    private Integer upperBound;

    private Integer lowerBound;

    private Integer rankRule;

    private Integer first;

    private Integer second;

    private Integer third;

    private Integer encourage;
}
