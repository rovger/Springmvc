package com.rovger.demo;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.WeakHashMap;

/**
 * @Description: 使用软引用SoftReference来实现Cache
 * @Author weijlu
 * @Date 2019/4/24 10:30
 */
public class EmployeeCache {
    private static volatile EmployeeCache cache;
    private WeakHashMap wmap;
    private ReferenceQueue queue;

    private EmployeeCache() {
        wmap = new WeakHashMap();
        queue = new ReferenceQueue();
    }

    public static EmployeeCache getInstance() {
        if (cache == null) {
            synchronized (EmployeeCache.class) {
                if (cache == null) {
                    cache = new EmployeeCache();
                }
            }
        }
        return cache;
    }

    public static void main(String[] args) {
        EmployeeCache cache = new EmployeeCache();
        Employee em = new Employee("001", "luweijie");

        cache.cacheEmployee(em);

//        Employee e1 = cache.getEmployee("002");
        Employee update = new Employee("001", "jj");
        cache.cacheEmployee(update);
        Employee e2 = cache.getEmployee("001");
        System.out.println(e2.getName());
    }

    private void cacheEmployee(Employee em) {
        cleanCache();
        EmployeeRef ref = new EmployeeRef(em, queue);
        wmap.put(em.getId(), ref);
    }

    private Employee getEmployee(String id) {
        Employee em = null;
        // 缓存中是否有该Employee实例的软引用，如果有，从软引用中取得
        if (wmap.containsKey(id)) {
            EmployeeRef ref = (EmployeeRef) wmap.get(id);
            System.out.println("Retrieve From EmployeeCache. id=" + id);
            em = ref.get();
        }
        // 如果没有软引用，或者从软引用中获得实例是null，重新构建一个实例
        if (em == null) {
            em = new Employee(id);
            System.out.println("Retrieve From EmployeeInfoCenter. id=" + id);
            this.cacheEmployee(em);
        }
        return em;
    }

    // 清除那些所软引用的Employee对象已经被回收的EmployeeRef对象
    private void cleanCache() {
        EmployeeRef ref = null;
        while ((ref = (EmployeeRef) queue.poll()) != null) {
            wmap.remove(ref._key);
        }
    }

    // 清除Cache内的全部内容
    public void clearCache() {
        cleanCache();
        wmap.clear();
        System.gc();
        System.runFinalization();
    }

}

class EmployeeRef extends SoftReference<Employee> {
    public String _key;
    // 继承SoftReference，使得每一个实例都具有可识别的标识。
    // 并且该标识与其在Map内的key相同。
    public EmployeeRef(Employee em, ReferenceQueue<? super Employee> q) {
        super(em, q);
        _key = em.getId();
    }
}

class Employee {
    private String id;// 雇员的标识号码
    private String name = "weijlu";// 雇员姓名
    private String department;// 该雇员所在部门
    private String Phone;// 该雇员联系电话
    private int salary;// 该雇员薪资
    private String origin;// 该雇员信息的来源

    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Employee(String id) {
        this.id=  id;
        getDataFromInfoCenter();
    }

    public void getDataFromInfoCenter() {
        // 和数据库建立连接井查询该雇员的信息，将查询结果赋值
        // 给name，department，plone，salary等变量
        // 同时将origin赋值为"From DataBase"
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

}