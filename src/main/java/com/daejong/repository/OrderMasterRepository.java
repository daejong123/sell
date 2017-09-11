package com.daejong.repository;

import com.daejong.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Daejong on 2017/9/11.
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    //根据买家的openid来查, 并且会有个分页.
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
