package utils;

import com.rovger.demo.utils.RetryUtil;
import org.junit.Test;

/**
 * Created by weijlu on 2017/11/29.
 */
public class RetryTest {

    @Test
    public void retry() throws Exception {
        String resp = RetryUtil.retry(new RetryUtil.CoreProcess() {
            @Override
            public <T> T process() throws Exception {
                String[] arrs = {"weijlu", "weijie"};
                return (T) arrs[3];
            }
        }, 3);
        System.out.println("retry result: " + resp);
    }
}
