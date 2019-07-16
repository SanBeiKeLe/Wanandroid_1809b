package com.wanghongli.myapplication.utils;


public class ObjectUtils {

    public static <T> T requireNonNull(T object,String message){
        if (object==null){
            throw new NullPointerException(message);
        }
        return  object;

    }
}
