package cn.edu.cqust;

import cn.edu.cqust.bean.CustomerInfo;
import cn.edu.cqust.bean.SignUpInfo;
import cn.edu.cqust.bean.vo.QoUpdateSignUp;
import cn.edu.cqust.bean.vo.RoSignUpList;
import cn.edu.cqust.service.SignUpInfoService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @project: HRMS_SpringbootDemo
 * @author: F.C.Tang
 * @date: 2020-08-10 11:31
 * @desc: service层测试
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HrmsApplication.class)
public class ServiceTest {

    @Autowired
    private SignUpInfoService signUpInfoService;


    /*****************************************************************
     *                       SignUpInfoService
     *****************************************************************/
    @Test
    @DisplayName("17: /signUp")
    public void test1() {
        SignUpInfo signUpInfo = new SignUpInfo();
        signUpInfo.setNote("service test");
        signUpInfoService.insert(signUpInfo);
    }

    @Test
    @DisplayName("18: /signUpList")
    public void test2() {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setName("");
        customerInfo.setIdNumber("");
        customerInfo.setPhoneNumber("");
        int pageIndex = 1;
        String employeePhone = "15998984122";
        List<RoSignUpList> res = signUpInfoService.findByMC1(customerInfo, pageIndex, employeePhone);
        res.forEach(System.out::println);
    }

    @Test
    @DisplayName("19: /signUpListCount")
    public void test3() {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setName("");
        customerInfo.setIdNumber("");
        customerInfo.setPhoneNumber("");
        String employeePhone = "15998984122";
        int res = signUpInfoService.countByMC1(customerInfo, employeePhone);
        System.out.println(res);
    }


    @Test
    @DisplayName("20: /updateSignUp")
    public void test4() {
        QoUpdateSignUp qo = new QoUpdateSignUp();
        qo.setSignUpInfoId(1);
        qo.setInterviewTime("回滚回滚回滚");
        qo.setAge(999);
        qo.setRecommendJob("回滚回滚回滚");
        System.out.println(signUpInfoService.updateSignUpInfoAndRelated(qo));
    }


}
