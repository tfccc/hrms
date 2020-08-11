package cn.edu.cqust.service.impl;

import cn.edu.cqust.bean.CustomerInfo;
import cn.edu.cqust.bean.PhoneCallList;
import cn.edu.cqust.bean.SignUpInfo;
import cn.edu.cqust.bean.vo.QoUpdateSignUp;
import cn.edu.cqust.bean.vo.RoSignUpList;
import cn.edu.cqust.dao.CustomerInfoDao;
import cn.edu.cqust.dao.PhoneCallListDao;
import cn.edu.cqust.dao.SignUpInfoDao;
import cn.edu.cqust.service.SignUpInfoService;
import cn.edu.cqust.util.BeanHelper;
import cn.edu.cqust.util.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @project: HRMS_SpringbootDemo
 * @author: F.C.Tang
 * @date: 2020-08-10 15:39
 * @desc:
 **/
@Service
public class SignUpInfoServiceImpl implements SignUpInfoService {
    @Resource
    private SignUpInfoDao signUpInfoDao;
    @Resource
    private CustomerInfoDao customerInfoDao;
    @Resource
    private PhoneCallListDao phoneCallListDao;

    @Override
    public Integer insert(SignUpInfo signUpInfo) {
        signUpInfo.setSignUpTime(DateUtil.getYMD());
        return signUpInfoDao.insert(signUpInfo);
    }

    @Override
    public List<RoSignUpList> findByMC1(CustomerInfo customerInfo,
                                        Integer pageNumber,
                                        String employeePhone) {
        return signUpInfoDao.findByMC1(customerInfo, (pageNumber - 1) * 10, employeePhone);
    }

    @Override
    public Integer countByMC1(CustomerInfo customerInfo, String employeePhone) {
        return signUpInfoDao.countByMC1(customerInfo, employeePhone);
    }

    @Override
    public Integer updateSignUpInfoAndRelated(QoUpdateSignUp qo) {
        SignUpInfo signUpInfo = signUpInfoDao.findById(qo.getSignUpInfoId());
        if (signUpInfo == null) {
            return -1;
        }
        int ciId = signUpInfo.getCustomerId();
        int pclId = signUpInfo.getPhoneCallListId();

        CustomerInfo ci = new CustomerInfo();
        PhoneCallList pcl = new PhoneCallList();
        SignUpInfo sui = new SignUpInfo();

        //set customer_info update param
        ci.setId(ciId);
        ci.setName(qo.getName());
        ci.setIdNumber(qo.getIdNumber());
        ci.setGender(qo.getGender());
        ci.setAge(qo.getAge());
        ci.setPhoneNumber(qo.getPhoneNumber());
        ci.setEducation(qo.getEducation());
        ci.setAddedPerson(qo.getAddress());
        ci.setProfessionalSkills(qo.getProfessionalSkills());
        ci.setHasCertificate(qo.getHasCertificate());
        ci.setIsDisability(qo.getIsDisability());
        //set phone_call_list update param
        pcl.setId(pclId);
        pcl.setRecommendEnterprise(qo.getRecommendEnterprise());
        pcl.setRecommendJob(qo.getRecommendJob());
        //set sign_up_info update param
        sui.setId(qo.getSignUpInfoId());
        sui.setSignUpTime(qo.getSignUpTime());
        sui.setInterviewTime(qo.getInterviewTime());
        //declare operationCode
        int s1 = -1;
        int s2 = -1;
        int s3 = -1;
        //check if all bean's field is empty, and get operationCode
        if (!BeanHelper.isEmptyBean(ci, "id"))
            s1 = customerInfoDao.update(ci);
        if (!BeanHelper.isEmptyBean(pcl, "id"))
            s2 = phoneCallListDao.update(pcl);
        if (!BeanHelper.isEmptyBean(sui, "id"))
            s3 = signUpInfoDao.update(sui);
        //return true if update at least 1 table
        return (s1 == 1 || s2 == 1 || s3 == 1) ? 1 : -1;
    }
}
