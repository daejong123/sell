package com.daejong.repository;

import com.daejong.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Daejong on 2017/9/10.
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    /**
     * 自定义查询方法.
     */

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
