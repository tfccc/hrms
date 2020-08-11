package cn.edu.cqust.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @project: HRMS
 * @author: F.C.Tang
 * @date: 2020-08-05 19:33
 * @desc: 日期工具
 **/
@SuppressWarnings("unused")
public class DateUtil {

    /**
     * @desc 获取当前年月日
     * @return 格式: 年-月-日
     */
    public static String getYMD() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

}
