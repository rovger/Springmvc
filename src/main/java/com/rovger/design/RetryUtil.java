package com.rovger.design;

/**
 * 这里实际上时匿名内部类的应用
 * Created by weijlu on 2017/11/29.
 */
public class RetryUtil {
    private static final String method = "Retry::retry()";
    public static <T> T retry(CoreProcess process, int retryNum) throws Exception {
        int retry = 0;
        while (retryNum > retry) {
            try {
                return process.process();
            } catch (Exception e) {
                retry++;
                System.out.println(method + " has execute " + retry + " times");
                if (retry > retryNum) {
                    throw new Exception(e);
                }
            }
        }
        return null;
    }

    public interface CoreProcess {
        <T> T process() throws Exception;
    }
}
