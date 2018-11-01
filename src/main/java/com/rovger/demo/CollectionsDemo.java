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
        Map<String, Integer> ageMap = new HashMap<>();

        Student student_1 = new Student("stu_001", 33);
        Student student_2 = new Student("stu_002", 18);
        Student student_3 = new Student("stu_003", 9);
        Student student_4 = new Student("stu_001", 81);
        Student student_5 = new Student("stu_001", 21);

        ageMap.put("weijlu", 27);
        ageMap.put("Eason", 4);
        ageMap.put("Lily", 28);
        ageMap.put("###", 0);
        ageMap.put("***", 10);

        studentList.add(student_1);
        studentList.add(student_2);
        studentList.add(student_3);
        studentList.add(student_4);
        studentList.add(student_5);
        //list before
        Iterator itList = studentList.iterator();
        while (itList.hasNext()) {
            System.out.println("List 处理排序前：" + itList.next().toString());
        }
        //map before
        for (Map.Entry entry : ageMap.entrySet()) {
            System.out.println("Map 处理排序前："+ entry.getKey() +":"+ entry.getValue());
        }
        /*for (int i=0; i<studentList.size(); i++) {
            System.out.println("Collections 处理排序前：" + studentList.get(i).toString());
        }*/
        System.out.println("==================分割线=====================");
        //list after
        Collections.sort(studentList, new Comparator<Student>() {
            public int compare(Student o1, Student o2) {
                return o1.getAge()-o2.getAge();
            }
        });
        //map after
        Set keySet = ageMap.entrySet();
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(keySet);
        Collections.sort(entryList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        for (int i=0; i<studentList.size(); i++) {
            System.out.println("List 处理排序后：" + studentList.get(i).toString());
        }
        for (Map.Entry<String, Integer> entry : entryList) {
            System.out.println("Map 处理排序后：" + entry.getKey() +":"+ entry.getValue());
        }
    }

}
