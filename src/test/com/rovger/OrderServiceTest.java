package com.rovger;

import com.rovger.event.OrderService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class OrderServiceTest extends TestCase {

    @Autowired
    private OrderService orderService;

    @Test
    public void testTestOrderEvent() {
        orderService.testOrderEvent();
    }
}