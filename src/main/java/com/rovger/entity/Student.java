package com.rovger.entity;

import java.io.Serializable;

/**
 * Created by weijlu on 2017/4/1.
 * CREATE TABLE STUDENT(
	 id INT PRIMARY KEY,
	 nickname VARCHAR(10) NOT NULL,
	 age SMALLINT,
	 grade SMALLINT,
	 score INT
	 ) ENGINE=INNODB DEFAULT CHARSET=utf8
 *
 */

/**
 * Student 由于是Dubbo框架下，且需要被consumer来引用，所以需要实现Serializable接口，否则对象无法写到consumer端
 */
public class Student implements Serializable {
	public Student() {
	}

	public Student(String nickname, int age) {
		this.nickname = nickname;
		this.age = age;
	}

	private int id;
	private String nickname;
	private int age;
	private int grade;
	private int score;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Student{" +
				"id=" + id +
				", nickname='" + nickname + '\'' +
				", age=" + age +
				", grade=" + grade +
				", score=" + score +
				'}';
	}
}
