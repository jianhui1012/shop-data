package com.example.common.utils;

public class KeyConst {

    //已借用
    public static final int STATUS_BORROWED = 1;
    //已归还
    public static final int STATUS_GIVE_BACK = 2;
    //请求借用
    public static final int STATUS_REQUEST_BORROW = 3;
    //享用
    public static final int STATUS_TAKE = 4;

    public static void main(String[] args) {
        String ip = "";
        String ipAddress = "sip:4002@10.91.8.108:5060";
        int front = ipAddress.indexOf("@") + 1;
        int end = ipAddress.lastIndexOf(":");
        ip = ipAddress.substring(front, end);
        System.out.println(ip);

        String resultStr="http://www.baidu.coma?kf-code=12222322223444444444444444455555555555555555555";
        int codestr = resultStr.indexOf("kf-code");
        String str = resultStr.substring(codestr + 8);
        if (str.length() >= 18) {
            System.out.println(str);
        }
    }
}
