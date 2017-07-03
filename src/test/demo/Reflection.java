package demo;

/**
 * Created by weijlu on 2017/7/3.
 */
public class Reflection {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        //1.
        Class c1 = Reflection.class;
        System.out.println("---"+ c1.newInstance());
        //2.
        Reflection c2 = new Reflection();
        System.out.println("---"+ c2.getClass());
        //3.
        Class c3 = Class.forName("demo.Reflection");
        System.out.println("---"+ c3.newInstance());

    }
}
