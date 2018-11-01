package com.rovger.dubbo;

import com.rovger.entity.Student;

/**
 * Dubbo provider expose interface
 */
public interface DubboService {

    Student getStudent(int id);
}
