package com.rovger.demo.reflect;

import com.rovger.entity.Student;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by weijlu on 2017/5/23.
 */
public class ReflectTest {

	private static Singleton singleton = null;

	public static void main(String[] args) {

		//反射获取单利模式下的类实例
		try {
			Class clazz = Class.forName(Singleton.class.getName());
			Constructor constructor = clazz.getDeclaredConstructor();
			constructor.setAccessible(true);
			singleton = (Singleton) constructor.newInstance();

			if (singleton != null) {
				System.out.println("======已经获取到Singleton实例======");
			} else {
				System.out.println("======无法获取到Singleton实例======");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


		//实例化一个Student类
		Student student1 = new Student();
		student1.setAge(25);
		student1.setNickname("Rovger");
		System.out.println(student1.getNickname()+"的年龄："+student1.getAge());
		//反射实例化
		try {
			Class cl1 = ReflectTest.class.getClassLoader().loadClass("com.rovger.entity.Student");
			Student student2 = (Student) cl1.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		//利用发射机制，修改student成员变量属性值
		try {
			//要求age属性，不能为private
			Student.class.getField("age").set(student1, 30);//1
//			Field field = Student.class.getDeclaredField("age");//2
//			field.set(student1, 30);
			student1.getClass().getField("age").set(student1, 40);
			System.out.println(student1.getNickname()+"的年龄："+student1.getAge());
		} catch (Exception e) {
			e.printStackTrace();
		}

		//获取Student类中所有方法
		Method[] methods = Student.class.getMethods();
		for (Method m : methods) {
//			System.out.println(m.getNickname());
		}
	}

	public void test() {
		System.out.println("当前类"+ this.getClass().getSimpleName());
	}
}
