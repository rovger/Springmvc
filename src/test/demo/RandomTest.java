package demo;

import java.util.Random;

/**
 * Created by weijlu on 2017/6/28.
 */
public class RandomTest {

    public static void main(String[] args) {
//        int random = new Random().nextInt(1000);

        int randomSleepTime = (int) Math.round(Math.random()*1000);

        System.out.println(randomSleepTime);

    }
}
