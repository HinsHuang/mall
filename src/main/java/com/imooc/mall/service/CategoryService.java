package com.imooc.mall.service;

import com.imooc.mall.vo.CategoryVo;
import com.imooc.mall.vo.ResponseVo;

import java.util.List;
import java.util.Set;

public interface CategoryService {

    public ResponseVo<List<CategoryVo>> selectAll();

    public void findSubCategoryId(Integer categoryId, Set<Integer> categoryIdSet);

}
