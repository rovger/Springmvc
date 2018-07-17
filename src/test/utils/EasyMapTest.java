package utils;

import com.rovger.demo.EasyMap;
import org.junit.Test;

/**
 * Created by weijlu on 2018/5/4.
 */
public class EasyMapTest {

    @Test
    public void test() {
        EasyMap map = new EasyMap();
        map.put("name", "weijlu");
        map.put("age", 25);

        System.out.println("before: name="+map.get("name"));
        System.out.println("before: name="+map.get("age"));
        System.out.println("==============modify================");
        map.put("name", "Rovger");
        System.out.println("after: name="+map.get("name"));
        System.out.println("before: name="+map.get("age"));
        System.out.println("==============remove================");
        map.remove("name");
        System.out.println("after: name="+map.get("name"));
        System.out.println("before: name="+map.get("age"));
    }

    @Test
    public void test_1() {
        System.out.println("===="+ Integer.MAX_VALUE);
    }

}
