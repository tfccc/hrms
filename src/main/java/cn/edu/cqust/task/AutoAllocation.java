package cn.edu.cqust.task;

import cn.edu.cqust.bean.PhoneCallList;
import cn.edu.cqust.dao.CustomerInfoDao;
import cn.edu.cqust.dao.EmployeeDao;
import cn.edu.cqust.dao.PhoneCallListDao;
import cn.edu.cqust.dao.SystemSettingsDao;
import cn.edu.cqust.util.Generator;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @project: HRMS_SpringbootDemo
 * @author: F.C.Tang
 * @date: 2020-08-08 10:50
 * @desc:
 **/
// TODO: 2020/8/9  5天内customerID唯一, 所有数据中两键唯一
// TODO: 2020/8/9  5天内customerID唯一, 所有数据中两键唯一
// TODO: 2020/8/9  5天内customerID唯一, 所有数据中两键唯一
@Component
@EnableScheduling
public class AutoAllocation {
    //每个业务员分配的数量
    private static int numPerPerson;
    //需分配的总数
    private static int numAllocation;
    //运行次数上限
    private static int allocateRoundLimit;
    //customer最大id
    private static int customerMaxId;

    //count
    private static int notExistCustomer = 0;
    private static int repetitionInResult = 0;
    private static int repetitionInOld = 0;
    private static int numOfRuns = 0;

    @Resource
    private EmployeeDao employeeDao;
    @Resource
    private PhoneCallListDao phoneCallListDao;
    @Resource
    private CustomerInfoDao customerInfoDao;
    @Resource
    private SystemSettingsDao systemSettingsDao;

    private Set<Integer> customers;
    private Set<Integer> employees;
    private Map<Integer, Integer> additionalPcl;
    private Map<Integer, Integer> pclFromSystemTimeLimit;


    /**
     * @desc 从数据库中获取分配所需的相关数据, 并初始化变量
     * @return void
     */
    public void initData() {
        //所有符合条件的customer_info的id
        customers = new HashSet<>(customerInfoDao.findIdByState("0"));
        //所有业务员+组长的id
        employees = new HashSet<>(employeeDao.findIdByPositions(new String[]{"组长", "业务员"}));
        //phone_call_list的所有旧数据(用于放入结果映射时比对)
        pclFromSystemTimeLimit = phoneCallListDao.findIdsBySystemTimeLimit()
                .stream()
                .collect(Collectors
                        .toMap(PhoneCallList::getCustomerId, PhoneCallList::getEmployeeId,
                        (Integer k1, Integer k2) -> k2));
        numPerPerson = systemSettingsDao.findAllocationQuantity();
        numAllocation = employees.size();
        allocateRoundLimit = numAllocation * numPerPerson * 10;
        customerMaxId = customerInfoDao.findMaxId();
    }


    /**
     * @desc 模拟分配算法
     * @return void
     */
    @SuppressWarnings("all")
    public void doAllocation() {
        additionalPcl = new LinkedHashMap<>();

        for (Integer employeeId : employees) {
            int tempEmployeeId = employeeId;
            int count = 0;
            while (count != numPerPerson) {
                int randomCustomerId = Generator.genRandomNumber(customerMaxId);
                Integer oldEmployeeId = pclFromSystemTimeLimit.get(randomCustomerId);
                oldEmployeeId = (oldEmployeeId == null ? -1 : oldEmployeeId);

                //存在该客户
                boolean isExistCustomer = customers.contains(randomCustomerId);
                //分配结果不重复
                boolean noRepetitionInAdditional = !additionalPcl.containsKey(randomCustomerId);
                //在旧名单内两键唯一
                boolean noRepetitionInOld = !(pclFromSystemTimeLimit.containsKey(randomCustomerId) && oldEmployeeId == tempEmployeeId);

                if (!isExistCustomer) {
                    notExistCustomer++;
                }
                if (!noRepetitionInAdditional) {
                    repetitionInResult++;
                }
                if (!noRepetitionInOld) {
                    repetitionInOld++;
                }

                if (isExistCustomer && noRepetitionInAdditional && noRepetitionInOld) {
                    additionalPcl.put(randomCustomerId, tempEmployeeId);
                    count++;
                }
                numOfRuns++;
                if (numOfRuns > allocateRoundLimit)
                    return;
            }
        }
    }

    /**
     * @desc 执行分配, 打印查询的数据
     * @return void
     */
    public void doAndPrintData() {
        initData();
        doAllocation();
        System.out.println("\n---------------------------------------");
        System.out.println("总运行次数: " + numOfRuns);
        System.out.println("公海不存在的客户: " + notExistCustomer);
        System.out.println("在分配结果中重复: " + repetitionInResult);
        System.out.println("旧数据中两键重复: " + repetitionInOld);
        System.out.println("---------------------------------------\n");
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.println("每个人分配的数量: " + numPerPerson);
        System.out.println("业务员组长的数量: " + numAllocation);
        System.out.println("分配次数限制: " + allocateRoundLimit);
        System.out.println("公海的最大ID: " + customerMaxId);
        System.out.println("符合条件的公海ID: \n" + customers);
        System.out.println("业务员和组长的ID: \n" + employees);
        System.out.println("旧电话名单 : \n" + pclFromSystemTimeLimit);
        System.out.println("最终生成结果, 容量" + additionalPcl.size() + " :\n" + additionalPcl);
        System.out.println("-------------------------------------------------------------------------------------------------\n");
    }


}
