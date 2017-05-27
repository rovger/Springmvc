package aop;

import com.rovger.aop.Sleepable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by weijlu on 2017/5/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class AOPTest {

	@Autowired
	private Sleepable sleep;

	@Test
	public void testAop(){
		sleep.sleep();
	}
}
