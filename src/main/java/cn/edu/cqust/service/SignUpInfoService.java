package cn.edu.cqust.service;

import cn.edu.cqust.bean.CustomerInfo;
import cn.edu.cqust.bean.SignUpInfo;
import cn.edu.cqust.bean.vo.QoUpdateSignUp;
import cn.edu.cqust.bean.vo.RoSignUpList;
import java.util.List;

/**
 * @project: HRMS_SpringbootDemo
 * @author: F.C.Tang
 * @date: 2020-08-10 15:27
 * @desc:
 **/
public interface SignUpInfoService {

    /**
     * @desc 插入
     * @param signUpInfo 需要插入的数据
     * @return 操作状态
     */
    Integer insert(SignUpInfo signUpInfo);

    /**
     * @desc 多条件查询1
     * @param customerInfo 三个字段:name, idNumber, phoneNumber
     * @param pageNumber 查询的起始位置
     * @param employeePhone session中业务员的电话
     * @return 结果列表
     */
    List<RoSignUpList> findByMC1(CustomerInfo customerInfo,
                                 Integer pageNumber,
                                 String employeePhone);

    /**
     * @desc 多条件查询1, 统计结果条数
     * @param customerInfo 三个字段:name, idNumber, phoneNumber
     * @param employeePhone session中业务员的电话
     * @return 结果数量
     */
    Integer countByMC1(CustomerInfo customerInfo,
                       String employeePhone);

    /**
     * @desc 根据sign_up_info的标识id, 修改该表以及两个外键关联表
     * @param qoUpdateSignUp 封装的查询对象
     * @return 操作状态
     */
    Integer updateSignUpInfoAndRelated(QoUpdateSignUp qoUpdateSignUp);



}
