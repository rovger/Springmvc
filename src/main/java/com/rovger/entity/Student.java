package com.rovger.entity;

/**
 * Created by weijlu on 2017/4/1.
 */
public class Student {

	public Student(){

	}

	public Student(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String name;
	public int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Student{" +
				"name='" + name + '\'' +
				", age=" + age +
				'}';
	}
}
