package com.gxu.gxuproject.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lcw on 2015/8/16 0016.
 */
public class MD5Cipher {
    public static String md5(String password){
        try {
            //1、信息摘要器
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] result = md.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            //2、将每一个byte和0xff做与运算，得到int类型的值
            for(byte b: result){
                int number = b & 0XFF;
                //3、将得到的每一个int，转换为十六进制，并返回String类型
                String str = Integer.toHexString(number);
                //4、如果不够8位，则在前面补全0
                if(str.length()==1){
                    buffer.append("0");
                }
                buffer.append(str);
            }
            return  buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
