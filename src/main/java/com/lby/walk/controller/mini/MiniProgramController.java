package com.lby.walk.controller.mini;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lby.walk.model.dto.req.ClockInDTO;
import com.lby.walk.model.dto.res.TUserActivityDTO;
import com.lby.walk.model.enums.UserTypeEnum;
import com.lby.walk.model.po.TUser;
import com.lby.walk.model.po.TUserActivity;
import com.lby.walk.service.ActivityUserService;
import com.lby.walk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

@RequestMapping("/api")
@RestController
@Slf4j
public class MiniProgramController {

    @Autowired
    private UserService userService;

    @Resource
    private ActivityUserService activityUserService;

    /**
     * 小程序登录
     * @param code
     * @param rawData
     * @param signature
     * @param encrypteData
     * @param iv
     * @param scene
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> doLogin(@RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "rawData", required = false) String rawData,
            @RequestParam(value = "signature", required = false) String signature,
            @RequestParam(value = "encrypteData", required = false) String encrypteData,
            @RequestParam(value = "iv", required = false) String iv,
            @RequestParam(value = "scene", required = false) String scene) {

        //System.out.println("签名"+signature);
        JSONObject sessionKeyOpenId = userService.getSessionKeyOrOpenId(code);
        //System.out.println("SessionAndopenId="+SessionKeyOpenId);
        String openId = sessionKeyOpenId.getString("openid");
        String sessionKey = sessionKeyOpenId.getString("session_key");
        //System.out.println("openid="+openId+",session_key="+sessionKey);
        TUser ouser = userService.findByOpenid(openId);
        TUser user = new TUser();
        if (ouser == null) {
            user.setType(UserTypeEnum.NORMAL.getCode());
            user.setOpenid(openId);
            userService.saveUser(user);
        }

        //JSONObject userInfo = userService.getUserInfo(encrypteData, sessionKey, iv);
        // System.out.println("根据解密算法获取的userInfo=" + userInfo);
        Map<String, Object> map = new HashMap<>();
        // map.put("userInfo",userInfo);
        map.put("sessionKey", sessionKey);
        map.put("openId", openId);
        return map;
    }

    /**
     * 打卡
     *
     * @return
     */
    @RequestMapping("/clockIn")
    public Map<String, Object> clockIn(ClockInDTO clockInDTO) {
        JSONObject json =
                userService.getUserInfo(clockInDTO.getEncryptedData(), clockInDTO.getSessionKey(), clockInDTO.getIv());
        JSONArray jsonArray = json.getJSONArray("stepInfoList");
        class Step implements Comparable<Step> {

            Integer step;

            Integer timestamp;

            Step(Integer step, Integer timestamp) {
                this.step = step;
                this.timestamp = timestamp;
            }

            @Override
            public int compareTo(Step o) {
                return timestamp < o.timestamp ? -1 : 0;
            }
        }
        List<Step> steps = new ArrayList<>();
        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;
            Integer st = jsonObject.getInteger("step");
            Integer time = jsonObject.getInteger("timestamp");
            Step ste = new Step(st, time);
            steps.add(ste);
        }
        //获取当天的步数
        clockInDTO.setStep(steps.stream().max(Comparator.naturalOrder()).map(ste -> ste.step).orElse(0));
        activityUserService.saveActivityUser(clockInDTO);
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        return map;
    }

    /**
     * 用户打卡列表
     * @param openId
     * @return
     */
    @RequestMapping("/list")
    public List<TUserActivityDTO> list(@RequestParam("openId") String openId){
        return activityUserService.getByOpenId(openId);
    }
}