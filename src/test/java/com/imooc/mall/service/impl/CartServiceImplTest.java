package com.imooc.mall.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imooc.mall.MallApplicationTest;
import com.imooc.mall.form.CartAddForm;
import com.imooc.mall.vo.CartVo;
import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class CartServiceImplTest extends MallApplicationTest {

    private Integer uid = 1;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Autowired
    private CartServiceImpl cartService;

    @Test
    public void add() {
        CartAddForm cartAddForm = new CartAddForm(29, true);
        ResponseVo<CartVo> responseVo = cartService.add(uid, cartAddForm);
        log.info("responseVo={}", gson.toJson(responseVo));
    }

    @Test
    public void list() {
        ResponseVo<CartVo> responseVo = cartService.list(uid);
        log.info("responseVo={}", gson.toJson(responseVo));
    }
}