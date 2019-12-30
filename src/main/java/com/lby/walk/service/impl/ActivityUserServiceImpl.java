package com.lby.walk.service.impl;

import com.lby.walk.dao.mapper.TActivityMapper;
import com.lby.walk.dao.mapper.TUserActivityMapper;
import com.lby.walk.model.dto.req.ClockInDTO;
import com.lby.walk.model.dto.res.TUserActivityDTO;
import com.lby.walk.model.po.TActivity;
import com.lby.walk.model.po.TUserActivity;
import com.lby.walk.service.ActivityUserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityUserServiceImpl implements ActivityUserService {

    @Resource
    TUserActivityMapper userActivityMapper;

    @Resource
    TActivityMapper activityMapper;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public void saveActivityUser(ClockInDTO clockInDTO) {
        //取当前正在进行的最新活动
        List<TActivity> activityList = activityMapper.selectAll();
        //取正在运行的状态的最新的一个
        TActivity activity = activityList.stream()
                .filter(tActivity -> tActivity.getStatus() == 0)
                .max(Comparator.naturalOrder())
                .orElse(null);
        if (null != activity) {
            TUserActivity userActivity = new TUserActivity();
            userActivity.setActivityId(activity.getId());
            LocalDate date = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date today = simpleDateFormat.parse(date.format(formatter));
                userActivity.setCtime(today);
                List<TUserActivity> list = userActivityMapper.select(userActivity);
                userActivity.setOpenid(clockInDTO.getOpenId());
                userActivity.setStatus(0);
                //如果记录存在则更新步数
                if (list.size() > 0) {
                    userActivity.setStep(clockInDTO.getStep());
                    userActivity.setId(list.get(0).getId());
                    userActivityMapper.updateByPrimaryKey(userActivity);
                    //如果记录不存在则插入新记录
                } else {
                    userActivity.setStep(clockInDTO.getStep());
                    userActivityMapper.insertSelective(userActivity);
                }
            } catch (ParseException e) {

            }
        }
    }

    @Override
    public List<TUserActivityDTO> getByOpenId(String openId) {
        TUserActivity userActivity = new TUserActivity();
        userActivity.setOpenid(openId);
        List<TUserActivity> userActivities = userActivityMapper.select(userActivity)
                .stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        List<TUserActivityDTO> userActivityDTOS =
                modelMapper.map(userActivities, new TypeToken<List<TUserActivityDTO>>() {
                }.getType());
        return userActivityDTOS.stream().map(acti -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            acti.setCtimeString(simpleDateFormat.format(acti.getCtime()));
            return acti;
        }).collect(Collectors.toList());
    }
}
