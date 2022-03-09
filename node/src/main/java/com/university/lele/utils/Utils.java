package com.university.lele.utils;

import java.util.UUID;

public class Utils {
    /**
     * 获取一个唯一的id
     * @return
     */
    public static String getUUID(){
        UUID uuid = UUID.randomUUID();
                return uuid.toString().replace("-", "");
            }

}
