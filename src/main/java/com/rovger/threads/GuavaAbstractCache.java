package com.rovger.threads;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.rovger.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Description: guava loading cache demo！
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2022年09月06日 16:13
 */
@Slf4j
public abstract class GuavaAbstractCache<E> implements InitializingBean {

    private ListeningExecutorService localCacheLSE = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

    protected abstract List<E> loadAll();

    protected abstract String getCacheKey();

    /**
     * 供外部访问的方法
     *
     * @return
     */
    public List<E> getAllFromLocalCache() {
        return localCache.getUnchecked(getCacheKey());
    }

    private LoadingCache<String, List<E>> localCache = CacheBuilder.newBuilder()
            .maximumSize(1)
            .refreshAfterWrite(300, TimeUnit.SECONDS)
            .build(new CacheLoader<String, List<E>>() {
                @Override
                public List<E> load(String key) throws Exception {
                    log.info("loading from remote, key is {}", key);
                    return GuavaAbstractCache.this.loadAll();
                }

                @Override
                public ListenableFuture<List<E>> reload(String key, List<E> oldValue) {
                    log.info("reloading from remote, key is {}", key);
                    return localCacheLSE.submit(GuavaAbstractCache.this::loadAll);
                }
            });

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(() -> {
            log.info("local cache {} initializing!", getCacheKey());
            loadAll();
            log.info("local cache {} initialized!", getCacheKey());
        }).start();
    }

    /**
     * 子类
     */
    @Component
    class GuavaTest extends GuavaAbstractCache<Student> {

        @Override
        protected List<Student> loadAll() {
            List<Student> studentList = Lists.newArrayList();
            studentList.add(new Student("weijie", 30));
            return studentList;
        }

        @Override
        protected String getCacheKey() {
            return "wei-jie";
        }
    }
}
