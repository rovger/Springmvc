package utils;

import com.rovger.utils.TimeType;
import org.junit.Test;

/**
 * Created by weijlu on 2017/5/26.
 */
public class EnumTest {

    @Test
    public void test() {
        System.out.println("============="+ TimeType.getByName("hour"));
    }
}
