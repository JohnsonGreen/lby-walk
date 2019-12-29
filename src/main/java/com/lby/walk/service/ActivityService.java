package com.lby.walk.service;

import com.lby.walk.model.dto.req.NewActivityDTO;
import com.lby.walk.model.po.TActivity;

import java.util.List;

public interface ActivityService {
    int newActivity(NewActivityDTO activity);

    List<TActivity> list();
}
