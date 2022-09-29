package com.rovger.demo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;

/**
 * @Description: TODO
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2021年01月25日 14:18
 */
public class AwareDamo implements BeanNameAware, BeanFactoryAware {

    @Override
    public void setBeanName(String s) {

    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

    }
}
