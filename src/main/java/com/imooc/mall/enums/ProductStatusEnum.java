package com.imooc.mall.enums;

import lombok.Getter;

@Getter
public enum ProductStatusEnum {
    //商品状态.1-在售 2-下架 3-删除

    ON_SALE(1),

    OFF_SALE(2),

    DELETE(3),

    ;

    private Integer code;

    private ProductStatusEnum(Integer code) {
        this.code = code;
    }
}
