/**
 * @项目名：crm-project
 * @创建人： Administrator
 * @创建时间： 2020-05-16
 * @公司： www.bjpowernode.com
 * @描述：TODO
 */
package com.bjpowernode.crm.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>NAME: DateUtils</p>
 * @author Administrator
 * @date 2020-05-16 14:35:24
 * @version 1.0
 */
public class DateUtils {
    public static String formatDateTime(Date d){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str=sdf.format(d);
        return str;
    }
}
