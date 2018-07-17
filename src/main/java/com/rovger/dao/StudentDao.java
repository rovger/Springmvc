package com.rovger.dao;

import com.rovger.entity.Student;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * DAO
 */
@Repository
public class StudentDao {
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    public Student getStudent(int id) {
        return sqlSessionTemplate.selectOne(this.getClass().getName() + ".getStudent", 1);
    }

    public void addStudent(Student student) {
        sqlSessionTemplate.insert(this.getClass().getName() + ".addStudent", student);
    }
}
