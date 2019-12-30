package com.lby.walk.service;

import com.lby.walk.model.dto.req.ClockInDTO;
import com.lby.walk.model.dto.res.TUserActivityDTO;

import java.util.List;

public interface ActivityUserService {

    void saveActivityUser(ClockInDTO clockInDTO);

    List<TUserActivityDTO> getByOpenId(String openId);
}
