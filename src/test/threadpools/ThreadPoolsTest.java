package threadpools;

import com.rovger.threads.ThreadUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weijlu on 2017/7/5.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:spring-context.xml")
public class ThreadPoolsTest {

    /*@Autowired
    ThreadPoolTaskExecutor executor;*/

    @Test
    public void testThreadPoolTaskExecutor() {
        List<Integer> tests = new ArrayList();
        for (int i=0; i<10000; i++) {
            tests.add(i);
        }
        try {
            List<Integer> respList = new SpecialThread().buildList(tests, 3000, 10, Integer.class);
            for (Integer resp : respList) {
                System.out.println(resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class SpecialThread extends ThreadUtil {

        @Override
        protected <X, T> List<X> getObject(List<T> list, Class<X> x, Object... others) {
            List<X> resp = new ArrayList<>();
            for (T num : list) {
                Integer cur = (Integer) num;
                if (cur % 1000 == 0) {
                    resp.add((X)cur);
                }
            }
            return resp;
        }
    }
}
