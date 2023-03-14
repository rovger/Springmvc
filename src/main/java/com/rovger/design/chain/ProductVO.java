package com.rovger.design.chain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description: 商品对象
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2023年02月27日 15:25
 */
@Data
@Builder
public class ProductVO {
    /**
     * 商品SKU，唯一
     */
    private Long skuId;
    /**
     * 商品名称
     */
    private String skuName;
    /**
     * 商品图片路径
     */
    private String imgPath;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 库存
     */
    private Integer stock;
}
