package com.daejong.service;

import com.daejong.dataobject.ProductInfo;
import com.daejong.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Daejong on 2017/9/10.
 */
public interface ProductService {

    ProductInfo findOne(String productId);

    //查询上架的所有商品列表
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);
}
