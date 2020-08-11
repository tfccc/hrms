package cn.edu.cqust.controller;

import cn.edu.cqust.bean.CustomerInfo;
import cn.edu.cqust.bean.PhoneCallList;
import cn.edu.cqust.bean.SignUpInfo;
import cn.edu.cqust.bean.vo.QoUpdateSignUp;
import cn.edu.cqust.bean.vo.RoSignUpList;
import cn.edu.cqust.service.SignUpInfoService;
import cn.edu.cqust.util.Generator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @project: HRMS_SpringbootDemo
 * @author: F.C.Tang
 * @date: 2020-08-10 16:23
 * @desc:
 **/
@RestController
@RequestMapping(produces = "application/json; charset=utf-8")
public class SignUpInfoController {
    
    @Resource
    private SignUpInfoService signUpInfoServiceImpl;
    // TODO: 2020/8/6 session域中获取号码
    private String phone = "15998984122";


    @PostMapping(path = "/signUp")
    public String insert(SignUpInfo signUpInfo) {
        return Generator.genJsonStatusCode(signUpInfoServiceImpl.insert(signUpInfo));
    }

    @PostMapping(path = "/signUpList")
    public List<RoSignUpList> findByMC1(CustomerInfo customerInfo,
                                        @RequestParam(defaultValue = "1") Integer pageIndex) {
        return signUpInfoServiceImpl.findByMC1(customerInfo, pageIndex, phone);
    }

    @PostMapping(path = "/signUpListCount")
    public String countByMC1(CustomerInfo customerInfo) {
        return Generator.genJsonObject("count", signUpInfoServiceImpl.countByMC1(customerInfo, phone));
    }

    @PostMapping(path = "/updateSignUp")
    public String updateSignUpInfoAndRelated(QoUpdateSignUp qo) {
        return Generator.genJsonStatusCode(signUpInfoServiceImpl.updateSignUpInfoAndRelated(qo));
    }

}
