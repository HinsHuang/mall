package com.imooc.mall.service.impl;

import com.imooc.mall.MallApplicationTest;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.enums.RoleEnum;
import com.imooc.mall.pojo.User;
import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Slf4j
public class UserServiceImplTest extends MallApplicationTest {

    public static final String USERNAME = "jack";

    public static final String EMAIL= "jack@qq.com";

    public static final String PASSWORD = "password";

    @Autowired
    private UserServiceImpl userService;

    @Before
    public void register() {
        User user = new User(USERNAME,PASSWORD,EMAIL, RoleEnum.CUSTOMER.getCode());
        ResponseVo<User> responseVo = userService.register(user);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void login() {
        ResponseVo<User> responseVo = userService.login(USERNAME, PASSWORD);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }
}