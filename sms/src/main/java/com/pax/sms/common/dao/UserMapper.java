package com.pax.sms.common.dao;

import com.pax.sms.common.domain.User;
import com.pax.sms.core.utils.EntityMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends EntityMapper<User> {
    User findUserByAccountName(@Param("accountName") String accountName);

    /**
     * 查询未激活的用户
     * @return
     */
    List<User> queryNotSendActiveEmailUser();

    /**
     * 查询重置密码的用户
     * @return
     */
    List<User> queryRestPswUser();
}