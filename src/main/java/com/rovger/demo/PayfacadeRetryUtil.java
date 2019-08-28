package com.rovger.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述：
 * 重试机制，重试次数区间：【MAX_RETRY， 2 * MAX_RETRY】
 *
 * @author weijie.lu
 * @create 2019-08-26
 */
public class PayfacadeRetryUtil {
    private static final Logger _log = LoggerFactory.getLogger(PayfacadeRetryUtil.class);
    private static final int MAX_RETRY = 3; // 事不过三

    public static <T> T retry(CoreProcesser processer) throws Exception {
        return retry(processer, MAX_RETRY);
    }

    public static <T> T retry(CoreProcesser processer, int retryNum) throws Exception {
        if (retryNum > 2 * MAX_RETRY) {
            throw new RuntimeException(String.format("Too many retries: %d, please reduce the retry times.", retryNum));
        }
        int times = 0;
        while (times < retryNum) {
            try {
                return processer.process();
            } catch (Exception e) {
                times++;
                _log.error(String.format("Has PayfacadeRetryUtil.retry %d times"), times);
                if (times == retryNum) {
                    _log.error("PayfacadeRetryUtil.retry", e);
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    public interface CoreProcesser {
        public <T> T process() throws Exception;
    }
}
