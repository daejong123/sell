package com.daejong.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品(包含类目)
 * Created by Daejong on 2017/9/10.
 */
@Data
public class ProductVO {

    @JsonProperty("name") //设置返回给前端时的名称.
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
