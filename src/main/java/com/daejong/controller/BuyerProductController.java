package com.daejong.controller;

import com.daejong.VO.ProductInfoVO;
import com.daejong.VO.ProductVO;
import com.daejong.VO.ResultVO;
import com.daejong.dataobject.ProductCategory;
import com.daejong.dataobject.ProductInfo;
import com.daejong.service.CategoryService;
import com.daejong.service.ProductService;
import com.daejong.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Daejong on 2017/9/10.
 */
@RestController
@RequestMapping(value = "/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/list")
    public Object list() {
        //1. 查询所有上架的商品
        List<ProductInfo> productInfoList = productService.findUpAll();

        //2. 查询类目(要一次性查询)
        //传统方法 for 循环
        //精简做法 lambda 表达式 *****推荐使用.
        List<Integer> categoryTypeList = productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        //3. 数据拼装
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    //spring提供的一个工具, 省的我们一个一个的获取属性设置属性.******推荐使用
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        //4. 返回给前端
//        ResultVO resultVO = new ResultVO();
//        resultVO.setData(productVOList);
//        resultVO.setCode(0);
//        resultVO.setMsg("success");
//        return resultVO;
        return ResultVOUtil.success(productVOList);
    }

}
