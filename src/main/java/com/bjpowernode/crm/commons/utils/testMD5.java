package com.bjpowernode.crm.commons.utils;

public class testMD5 {

    public static void main(String[] args) {
       String ps= MD5Util.getMD5(MD5Util.getMD5("admin"+"jerry"));
       System.out.println(ps);
    }
}
