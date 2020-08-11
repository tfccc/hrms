package cn.edu.cqust;

import cn.edu.cqust.dao.CustomerInfoDao;
import cn.edu.cqust.dao.EmployeeDao;
import cn.edu.cqust.dao.PhoneCallListDao;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HrmsApplication.class)
class HrmsApplicationTests {

    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private PhoneCallListDao phoneCallListDao;
    @Autowired
    private CustomerInfoDao customerInfoDao;

    @Test
    public void test1() {
        System.out.println(employeeDao.
                countByPositions(new String[]{"业务员", "组长"}));
    }

    @Test
    public void test2() {
        System.out.println(employeeDao.
                findIdByPositions(new String[]{"业务员", "组长"}));
    }


    @Test
    public void test3() {
        phoneCallListDao.findIdsBySystemTimeLimit()
                .forEach(o -> {
                    System.out.print(o.getCustomerId() + "_");
                    System.out.println(o.getEmployeeId());
                });
    }

    @Test
    public void test4() {
        Set<Integer> set = new HashSet<>(customerInfoDao.findIdByState("0"));
        System.out.println(set);
    }

    @Test
    public void test5() {
        System.out.println(customerInfoDao.findMaxId());
    }


    /*************************************************************
     *                  运行时间/初始化、销毁(junit5)
     *************************************************************/
    private static long sTime;

    @BeforeAll
    @DisplayName("开始时间/")
    public static void start() {
        sTime = System.currentTimeMillis();
    }

    @AfterAll
    @DisplayName("结束时间/")
    public static void end() {
        long eTime = System.currentTimeMillis();
        System.out.println("耗时: " + (eTime - sTime) + "ms");
    }
}
