package com.lby.walk.controller;

import com.lby.walk.model.dto.req.NewActivityDTO;
import com.lby.walk.model.po.TActivity;
import com.lby.walk.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    /**
     * 返回新建页面
     * @return
     */
    @GetMapping("/new")
    public String nw() {
        return "/new";
    }

    /**
     *
     * 返回活动列表
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(Model model) {
        List<TActivity> activityList = activityService.list();
        model.addAttribute("activities",activityList);
        return "/list";
    }

    /**
     * 提交一次健步走活动
     * @param newActivityDTO
     * @return
     */
    @PostMapping("/submit")
    public String submit(NewActivityDTO newActivityDTO){
        newActivityDTO.setUserId(3);      //管理员id
        activityService.newActivity(newActivityDTO);
        return "/default";
    }
}
