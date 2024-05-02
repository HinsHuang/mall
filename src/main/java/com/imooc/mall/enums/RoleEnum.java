package com.imooc.mall.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
    // 0-管理员, 1-普通用户

    ADMIN(0),

    CUSTOMER(1),

    ;

    private Integer code;

    private RoleEnum(Integer code) {
        this.code = code;
    }

}
