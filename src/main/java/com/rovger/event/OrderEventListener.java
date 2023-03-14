package com.rovger.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @Description: 目标事件OrderEvent监听类，可以通过以下几种方式实现：
 * 
 * 1、实现ApplicationListener接口，并重写onApplicationEvent()方法来处理事件
 * 2、基于@EventListener或@TransactionalEventListener注解实现
 * 3、基于@Async异步事件处理，可以提高程序的响应速度。往往与@EventListener或@TransactionalEventListener搭配使用
 *
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2023年02月21日 09:44
 */
@Component
public class OrderEventListener {

    @EventListener
    public void handleOrderEvent(OrderEvent orderEvent) {
        System.out.println("已接收到到一个orderEvent：" + orderEvent);
    }

    /*@TransactionalEventListener
    public void handleTransactionalOrderEvent() {
        // 接收上游@Transactional事务提交后事件
    }*/

}
