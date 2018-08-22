package com.pax.sms.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Created by yyyty
 * On 2017/8/23 11:21
 * Description
 */
public class FastJsonObject extends JSONObject {

    //重写toString,使返回保留空值

    @Override
    public String toString() {
        return  JSON.toJSONString(this, SerializerFeature.WriteMapNullValue);
    //    return super.toString();
    }
}
