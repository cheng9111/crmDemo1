/**
 * @项目名：crm-project
 * @创建人： Administrator
 * @创建时间： 2020-05-21
 * @公司： www.bjpowernode.com
 * @描述：TODO
 */
package com.bjpowernode.crm.commons.utils;

import java.util.UUID;

/**
 * <p>NAME: UUIDUtils</p>
 * @author Administrator
 * @date 2020-05-21 11:09:07
 * @version 1.0
 */
public class UUIDUtils {
    public static void main(String[] args) {
       String str=UUID.randomUUID().toString();
        str=str.replaceAll("-","");
        System.out.println(str);
    }

    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
