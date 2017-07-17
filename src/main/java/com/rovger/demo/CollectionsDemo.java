package com.rovger.demo;

import com.rovger.entity.Student;

import java.util.*;

/**
 * Created by weijlu on 2017/7/13.
 * 集合排序
 */
public class CollectionsDemo {

    public static void main(String[] args) {

        List<Student> studentList = new ArrayList<Student>();

        Student student_1 = new Student("stu_001", 33);
        Student student_2 = new Student("stu_002", 18);
        Student student_3 = new Student("stu_003", 9);
        Student student_4 = new Student("stu_001", 81);
        Student student_5 = new Student("stu_001", 21);

        studentList.add(student_1);
        studentList.add(student_2);
        studentList.add(student_3);
        studentList.add(student_4);
        studentList.add(student_5);
        //before
        Iterator it = studentList.iterator();
        while (it.hasNext()) {
            System.out.println("Collections 处理排序前：" + it.next().toString());
        }
        /*for (int i=0; i<studentList.size(); i++) {
            System.out.println("Collections 处理排序前：" + studentList.get(i).toString());
        }*/
        System.out.println("==================分割线=====================");
        //after
        Collections.sort(studentList, new Comparator<Student>() {
            public int compare(Student o1, Student o2) {
                return o1.getAge()-o2.getAge();
            }
        });
        for (int i=0; i<studentList.size(); i++) {
            System.out.println("Collections 处理排序后：" + studentList.get(i).toString());
        }
    }

}
