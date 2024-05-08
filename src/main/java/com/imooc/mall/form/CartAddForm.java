package com.imooc.mall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CartAddForm {

    @NotNull
    private Integer productId;

    private Boolean selected = true;

    public CartAddForm(@NotNull Integer productId, Boolean selected) {
        this.productId = productId;
        this.selected = selected;
    }
}
