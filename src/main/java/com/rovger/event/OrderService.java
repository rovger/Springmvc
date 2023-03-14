package com.rovger.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @Description: 订单业务服务
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2023年02月21日 09:54
 */
@Service
public class OrderService {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void testOrderEvent() {

        // 订单逻辑跳过...

        System.out.println("开始orderEvent...");
        // 发布事件或跨领域间数据一致性同步
        OrderEvent orderEvent = new OrderEvent("order-8888", new BigDecimal("12.50"), 1);
        eventPublisher.publishEvent(orderEvent);
        System.out.println("已发布一个orderEvent...");
    }
}
