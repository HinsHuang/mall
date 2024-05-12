package com.imooc.mall.service.impl;

import com.imooc.mall.MallApplicationTest;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.form.ShippingForm;
import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.junit.Assert.*;

@Slf4j
public class ShippingServiceImplTest extends MallApplicationTest {

    @Autowired
    private ShippingServiceImpl shippingService;

    private Integer uid = 1;

    private Integer shippingId;

    @Test
    public void add() {
        ShippingForm shippingForm = new ShippingForm();
        shippingForm.setReceiverAddress("广州");
        shippingForm.setReceiverCity("广州");
        shippingForm.setReceiverDistrict("白云");
        shippingForm.setReceiverMobile("020123456");
        shippingForm.setReceiverPhone("1591111111");
        shippingForm.setReceiverZip("524431");
        shippingForm.setReceiverName("辉哥");
        shippingForm.setReceiverProvince("广东");
        ResponseVo<Map<String, Integer>> responseVo = shippingService.add(uid, shippingForm);
        log.info("ResponseVo={}", responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void delete() {
    }

    @Test
    public void update() {
    }

    @Test
    public void list() {
    }
}