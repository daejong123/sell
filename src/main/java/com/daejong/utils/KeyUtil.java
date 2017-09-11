package com.daejong.utils;

import java.util.Random;

/**
 * Created by Daejong on 2017/9/11.
 */
public class KeyUtil {

    /**
     * 生成唯一的主键.
     * 格式: 时间+随机数
     */
    public static synchronized String getUniqueKey() {
        Random random = new Random();
        //生成6位随机数
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }

}
