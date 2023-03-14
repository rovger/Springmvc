package com.rovger.event;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description: TODO
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2023年02月21日 09:42
 */
@Data
@AllArgsConstructor
public class OrderEvent {

    private String orderNo;
    private BigDecimal amount;
    private int orderStatus;

}
