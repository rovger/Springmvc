package com.rovger.design.chain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Description: 处理器配置类
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2023年02月27日 15:27
 */
@AllArgsConstructor
@Data
public class ProductCheckHandlerConfig {
    /**
     * 处理器Bean名称
     */
    private String handler;
    /**
     * 下一个处理器
     */
    private ProductCheckHandlerConfig next;
    /**
     * 是否降级
     */
    private Boolean down = Boolean.FALSE;
}
