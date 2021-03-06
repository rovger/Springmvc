package com.rovger.service;

import com.rovger.entity.Student;

public interface IStudentService extends IPerson {

    public void addStudent(Student student);

    public Student getStudent(int id);
}
