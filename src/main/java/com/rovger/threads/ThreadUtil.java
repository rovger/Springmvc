package com.rovger.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by weijlu on 2017/11/29.
 */
public abstract class ThreadUtil {

    private static final int MAX_POOL_SIZE_LIMIT = 50;
    private static final String OUT_OF_POOL_SIZE = "Privide poolSiez has beyond the maximum allowed " + MAX_POOL_SIZE_LIMIT;

    public <X, T> List<X> buildList(List<T> list, int batchSize, int poolSize, final Class<X> x, final Object... others) throws Exception {
        if (poolSize > MAX_POOL_SIZE_LIMIT) throw new Exception(OUT_OF_POOL_SIZE);
        ExecutorService threadPool = Executors.newFixedThreadPool(poolSize);
        List<Future<List<X>>> futureList = new ArrayList<>();
        List<X> resp = new ArrayList<>();
        int startIndex = 0;
        while (list.size() > startIndex) {
            int endIndex = startIndex + batchSize;
            final List<T> subList = list.subList(startIndex, Math.min(endIndex, list.size()));
            futureList.add(threadPool.submit(new Callable<List<X>>() {
                @Override
                public List<X> call() throws Exception {
                    return getObject(subList, x, others);
                }
            }));
            startIndex = endIndex;
        }
        threadPool.shutdown();
        for (Future<List<X>> future : futureList) {
            try {
                if (future.get() != null) {
                    resp.addAll(future.get());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resp;
    }

    protected abstract <X, T> List<X> getObject(List<T> list, Class<X> x, Object... others);
}
