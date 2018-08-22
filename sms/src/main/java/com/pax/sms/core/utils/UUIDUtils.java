package com.pax.sms.core.utils;

import java.util.UUID;

/**
 * com.pax.sms.utils
 *
 * @author yyyty
 * @time :  2018/6/20
 * @description:
 */
public class UUIDUtils {
    public static String getUUID32(){
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        return uuid;
    }
}
