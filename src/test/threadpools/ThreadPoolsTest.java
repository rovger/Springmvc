package threadpools;

import com.rovger.task.threadPools.StartTaskThread;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by weijlu on 2017/7/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-context.xml")
public class ThreadPoolsTest {

    @Autowired
    ThreadPoolTaskExecutor executor;

    @Test
    public void testThreadPoolTaskExecutor() {
        for (int i=0; i<10; i++) {
            new Thread(new StartTaskThread(executor, i)).start();
        }
    }
}
