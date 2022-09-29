package com.rovger.threads;

/**
 * @Description: 活锁例子
 * 创建一个勺子类，有且只有一个。
 * 丈夫和妻子用餐时，需要使用勺子，这时只能有一人持有，也就是说同一时刻只有一个人能够进餐。
 * 但是丈夫和妻子互相谦让，都想让对方先吃，所以勺子一直传递来传递去，谁都没法用餐。
 * @Author weijlu
 * @Date 2019/2/18 16:47
 */
public class AliveLockDemo {

    public static void main(String[] args) {
        //定义2个用餐类
        Diner husband = new Diner(true, "husband");
        Diner wife = new Diner(true, "wife");
        //一开始勺子有妻子拿到
        Spoon spoon = new Spoon(wife);

        //丈夫用餐线程
        Thread h = new Thread(new Runnable() {
            @Override
            public void run() {
                husband.eatWith(wife, spoon);
            }
        });
        h.start();

        //妻子用餐线程
        Thread w = new Thread(new Runnable() {
            @Override
            public void run() {
                wife.eatWith(husband, spoon);
            }
        });
        w.start();

        try {
            Thread.sleep(10000);//让主线程睡眠10s
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //手动中断线程
        h.interrupt();
        w.interrupt();

        /*try {
            h.join();//阻塞，直到Main线程执行结束
            w.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

}

//定义一个勺子类
class Spoon {
    Diner owner;//吃饭人

    public Spoon(Diner owner) {
        this.owner = owner;
    }

    public void setOwner(Diner owner) {//对勺子设置拥有者
        this.owner = owner;
    }

    public String getOwnerName() {//获取拥有勺子人名
        return owner.getName();
    }

    public void use() {//表示正在用餐
        System.out.println(owner.getName() + " use spoon eating...");
    }
}

class Diner {
    private boolean isHungry;
    private String name;

    public Diner(boolean isHungry, String name) {
        this.isHungry = isHungry;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    //理解为当前对象和谁一起用餐
    public void eatWith(Diner diner, Spoon spoon) {
        try {
            synchronized (spoon) {
                while (isHungry) {
                    //如果用餐者和勺子拥有者不是同一个人，则等待
                    while (!spoon.getOwnerName().equals(name)) {
                        spoon.wait();
                    }
                    //另一个人饿了，那就将勺子分给他，然后通知他用餐
                    if (diner.isHungry) {
                        System.out.println("I am " + name + ", and my " + diner.getName() + " is hungry, I should give it to him(her).");
                        spoon.setOwner(diner);
                        spoon.notifyAll();
                    } else {
                        //自己用餐
                        spoon.use();
                        isHungry = false;
                    }
                    Thread.sleep(500);
                }
            }
        } catch (InterruptedException ie) {
            System.out.println(name + " is interrupted.");
        }
    }
}