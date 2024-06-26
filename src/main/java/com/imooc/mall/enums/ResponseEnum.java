package com.imooc.mall.enums;

import lombok.Getter;

@Getter
public enum ResponseEnum {

    ERROR(-1,"服务端异常"),

    SUCCESS(0,"成功"),

    PASSWORD_ERROR(1,"密码错误"),

    USERNAME_EXIST(2,"用户已存在"),

    PARAM_ERROR(3,"参数错误"),

    EMAIL_EXIST(4,"邮箱已存在"),

    NEED_LOGIN(10,"用户未登录,请先登录"),

    USERNAME_OR_PASSWORD_ERROR(11,"用户名或者密码错误"),

    PRODUCT_OFF_SALE_OR_DELETE(12,"该商品已下架或删除"),

    PRODUCT_NOT_EXIST(13,"商品不存在"),

    PRODUCT_STOCK_ERROR(14,"商品库存错误"),

    CART_PRODUCT_NOT_EXIST(15,"购物车无此商品"),

    DELETE_SHIPPING_FAIL(16,"删除收货地址失败"),

    SHIPPING_NOT_EXIST(17, "收货地址不存在"),

    ;

    private Integer code;

    private String desc;

    private ResponseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
