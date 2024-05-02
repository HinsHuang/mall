package com.imooc.mall.service.impl;

import com.imooc.mall.consts.MallConst;
import com.imooc.mall.dao.CategoryMapper;
import com.imooc.mall.pojo.Category;
import com.imooc.mall.service.CategoryService;
import com.imooc.mall.vo.CategoryVo;
import com.imooc.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseVo<List<CategoryVo>> selectAll() {
        List<CategoryVo> categoryVoList = new ArrayList<>();
        List<Category> categoryList = categoryMapper.selectAll();
        //找出所有根节点
        for (Category category : categoryList) {
            if (category.getParentId().equals(MallConst.ROOT_PARENT_ID)) {
                CategoryVo categoryVo = category2CategoryVo(category);
                categoryVoList.add(categoryVo);
            }
        }
        categoryVoList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());
        //递归寻找每层目录的子目录
        findSubCategory(categoryVoList, categoryList);
        return ResponseVo.success(categoryVoList);
    }

    @Override
    public void findSubCategoryId(Integer categoryId, Set<Integer> categoryIdSet) {
        List<Category> categoryList = categoryMapper.selectAll();
        findSubCategoryId(categoryId, categoryIdSet, categoryList);
    }

    private void findSubCategoryId(Integer categoryId, Set<Integer> subCategoryIdSet, List<Category> categoryList) {
        for (Category category : categoryList) {
            if (category.getParentId().equals(categoryId)) {
                subCategoryIdSet.add(category.getId());
                findSubCategoryId(category.getId(), subCategoryIdSet, categoryList);
            }
        }
    }

    private void findSubCategory(List<CategoryVo> categoryVoList, List<Category> categoryList) {
        for (CategoryVo categoryVo : categoryVoList) {
            List<CategoryVo> subCategoryVoList = new ArrayList<>();
            for (Category category : categoryList) {
                if (categoryVo.getId().equals(category.getParentId())) {
                    CategoryVo subCategoryVo = category2CategoryVo(category);
                    subCategoryVoList.add(subCategoryVo);
                }
            }
            if (subCategoryVoList.size() > 0) {
                findSubCategory(subCategoryVoList, categoryList);
                subCategoryVoList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());
                categoryVo.setSubCategories(subCategoryVoList);
            }
        }
    }

    private CategoryVo category2CategoryVo(Category category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }
}
