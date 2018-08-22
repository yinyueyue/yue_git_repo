package com.pax.sms.core.utils;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * com.pax.sms.utils
 *
 * @author yyyty
 * @time :  2018/6/13
 * @description: 通用mapper接口
 */
public interface EntityMapper <T> extends Mapper<T>, MySqlMapper<T> {
    //FIXME 特别注意，该接口不能被扫描到，否则会出错
}
