package cn.edu.cqust;

import cn.edu.cqust.bean.CustomerInfo;
import cn.edu.cqust.bean.PhoneCallList;
import cn.edu.cqust.bean.SignUpInfo;
import cn.edu.cqust.bean.vo.RoSignUpList;
import cn.edu.cqust.dao.CustomerInfoDao;
import cn.edu.cqust.dao.PhoneCallListDao;
import cn.edu.cqust.dao.SignUpInfoDao;
import cn.edu.cqust.util.DateUtil;
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
 * @date: 2020-08-10 10:29
 * @desc: dao层测试
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HrmsApplication.class)
public class DaoTest {

    @Autowired
    private SignUpInfoDao signUpInfoDao;
    @Autowired
    private CustomerInfoDao customerInfoDao;
    @Autowired
    private PhoneCallListDao phoneCallListDao;

    /*****************************************************************
     *                       SignUpInfoDao
     *****************************************************************/
    @Test
    @DisplayName("17: /signUp --> 插入数据")
    public void test4() {
        SignUpInfo signUpInfo = new SignUpInfo();
        signUpInfo.setSignUpTime(DateUtil.getYMD());
        signUpInfo.setNote("note*********");
        signUpInfoDao.insert(signUpInfo);
    }

    @Test
    @DisplayName("18: /signUpList")
    public void test1() {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setName("");
        customerInfo.setIdNumber("");
        customerInfo.setPhoneNumber("");
        int startIndex = 0;
        String employeePhone = "15998984122";
        List<RoSignUpList> res = signUpInfoDao.findByMC1(customerInfo, startIndex, employeePhone);
        res.forEach(System.out::println);
    }

    @Test
    @DisplayName("19: /signUpListCount")
    public void test2() {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setName("");
        customerInfo.setIdNumber("");
        customerInfo.setPhoneNumber("");
        String employeePhone = "15998984122";
        int res = signUpInfoDao.countByMC1(customerInfo, employeePhone);
        System.out.println(res);
    }

    @Test
    @DisplayName("20: /updateSignUp --> 查询3外键")
    public void test3_1() {
        SignUpInfo res = signUpInfoDao.findById(1);
        System.out.println(res);
    }

    @Test
    @DisplayName("20: /updateSignUp --> 更新SignUpInfo")
    public void test3_2() {
        SignUpInfo signUpInfo = new SignUpInfo();
        signUpInfo.setId(34);
        signUpInfo.setInterviewTime("2020-08-10");
        signUpInfo.setNote("new note");
        System.out.println(signUpInfoDao.update(signUpInfo));
    }

    @Test
    @DisplayName("20: /updateSignUp --> 更新CustomerInfo")
    public void test3_3() {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setAge(99);
        customerInfo.setId(1);
        System.out.println(customerInfoDao.update(customerInfo));
    }

    @Test
    @DisplayName("20: /updateSignUp --> 更新PCL")
    public void test3_4() {
        PhoneCallList phoneCallList = new PhoneCallList();
        phoneCallList.setId(301);
        phoneCallList.setRecommendJob("后端工程师");
        System.out.println(phoneCallListDao.update(phoneCallList));
    }

}
