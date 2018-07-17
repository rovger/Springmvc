package utils;

import com.rovger.entity.Student;
import com.rovger.service.IStudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class MybatisTest {

    @Autowired
    private IStudentService service;

    @Test
    public void testMybatis() {
        Student student = new Student();
        student.setId(2);
        student.setNickname("Eason");
        student.setAge(3);
        student.setGrade(1);
        student.setScore(95);
        service.addStudent(student);
        /*Student stu = service.getStudent(1);
        System.out.println("Mybatis query result:"+ stu.toString());*/
    }
}
