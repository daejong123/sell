package com.daejong.repository;

import com.daejong.dataobject.ProductCategory;
import com.daejong.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Daejong on 2017/9/10.
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    /**
     * 查看商品的状态
     */
    List<ProductInfo> findByProductStatus(Integer productStatus);
}

