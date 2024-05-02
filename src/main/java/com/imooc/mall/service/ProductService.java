package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.vo.ProductDetailVo;
import com.imooc.mall.vo.ResponseVo;

public interface ProductService {

    public ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);

    public ResponseVo<ProductDetailVo> detail(Integer productId);
}
