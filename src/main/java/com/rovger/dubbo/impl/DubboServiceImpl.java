package com.rovger.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.rovger.dao.StudentDao;
import com.rovger.dubbo.DubboService;
import com.rovger.entity.Student;

import javax.annotation.Resource;

//@Service  //该Service注解是dubbo的注解，不是spring的。若使用xml配置方式暴露接口，则不需要该注解。
public class DubboServiceImpl implements DubboService {
    @Resource
    private StudentDao studentDao;

    public Student getStudent(int id) {
        return studentDao.getStudent(id);
    }
}
