package com.rovger.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.rovger.entity.Student;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 基于JVM内存缓存技术栈：Caffeine
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2022年09月05日 17:40
 */
public class CaffeineCache {

    private static Cache<String, List<Student>> stuCache = Caffeine
            .newBuilder()
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .maximumSize(1000)
            .build();

    public static List<Student> main(String[] args) {

        List<Student> students = stuCache.getIfPresent("weijie");
        if (CollectionUtils.isEmpty(students)) {
            students.add(new Student("jj", 30));

            stuCache.put("weijie", students);
        }
        return students;
    }

}
