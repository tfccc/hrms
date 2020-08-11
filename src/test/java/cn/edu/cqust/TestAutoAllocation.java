package cn.edu.cqust;

import cn.edu.cqust.task.AutoAllocation;
import org.junit.jupiter.api.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @project: HRMS_SpringbootDemo
 * @author: F.C.Tang
 * @date: 2020-08-08 21:50
 * @desc:
 **/
// TODO: 2020/8/9 5天内customerID唯一, 所有数据中两键唯一
// TODO: 2020/8/9 5天内customerID唯一, 所有数据中两键唯一
// TODO: 2020/8/9 5天内customerID唯一, 所有数据中两键唯一
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HrmsApplication.class)
public class TestAutoAllocation {

    @Autowired
    private AutoAllocation allocation;

    @Test
    @DisplayName("")
    public void test1() {
        // TODO: 2020/8/9 测试
        allocation.doAndPrintData();
    }

    @Test
    @DisplayName("")
    public void test2() {
        System.out.println(1);
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
