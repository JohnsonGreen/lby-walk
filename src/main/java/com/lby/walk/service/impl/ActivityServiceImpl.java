package com.lby.walk.service.impl;

import com.lby.walk.dao.mapper.TActivityMapper;
import com.lby.walk.model.dto.req.NewActivityDTO;
import com.lby.walk.model.po.TActivity;
import com.lby.walk.service.ActivityService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Resource
    TActivityMapper tActivityMapper;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public int newActivity(NewActivityDTO activity) {
        TActivity tActivity = modelMapper.map(activity, TActivity.class);
        tActivity.setStatus(0);
        tActivity.setId(null);
        return tActivityMapper.insert(tActivity);
    }

    @Override
    public List<TActivity> list() {
        return tActivityMapper.selectAll().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }
}
