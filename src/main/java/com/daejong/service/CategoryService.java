package com.daejong.service;

import com.daejong.dataobject.ProductCategory;

import java.util.List;

/**
 * Created by Daejong on 2017/9/10.
 */
public interface CategoryService {

    /**
     *给后台管理用的
     */
    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    /**
     * 给买家端用的
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryType);

    ProductCategory save(ProductCategory productCategory);
}
