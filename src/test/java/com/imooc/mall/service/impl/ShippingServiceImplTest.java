package com.imooc.mall.service.impl;

import com.imooc.mall.MallApplicationTest;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.form.ShippingForm;
import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Slf4j
public class ShippingServiceImplTest extends MallApplicationTest {

    @Autowired
    private ShippingServiceImpl shippingService;

    private Integer uid = 1;

    private Integer shippingId;

    private ShippingForm form;

    private void init() {
        ShippingForm shippingForm = new ShippingForm();
        shippingForm.setReceiverAddress("广州");
        shippingForm.setReceiverCity("广州");
        shippingForm.setReceiverDistrict("白云");
        shippingForm.setReceiverMobile("020123456");
        shippingForm.setReceiverPhone("1591111111");
        shippingForm.setReceiverZip("524431");
        shippingForm.setReceiverName("辉哥");
        shippingForm.setReceiverProvince("广东");
        this.form = shippingForm;
    }

    @Before
    public void add() {
        init();
        ResponseVo<Map<String, Integer>> responseVo = shippingService.add(uid, form);
        shippingId = responseVo.getData().get("shippingId");
        log.info("ResponseVo={}", responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @After
    public void delete() {
        ResponseVo responseVo = shippingService.delete(uid, shippingId);
        log.info("ResponseVo={}", responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void update() {
        form.setReceiverProvince("北京");
        ResponseVo responseVo = shippingService.update(uid, shippingId, form);
        log.info("ResponseVo={}", responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void list() {
        ResponseVo responseVo = shippingService.list(uid, 1, 10);
        log.info("ResponseVo={}", responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }
}