package com.rovger.task.threadPools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by weijlu on 2017/7/7.
 * 线程池，批处理通用类
 */
public abstract class ThreadPoolsUtil<X, T> {

    private final static int MAX_POOL_NUMBER=50;
    private final static String MAX_POOL_NUMBER_EXCEED="Exceed max allowed pool number 50";

    public List<X> buildList(List<T> list, int poolSize, int batchSize, final Class<X> x, final Object... otherParameter) throws Exception {
        List<X> responseList = null;
        if (poolSize>MAX_POOL_NUMBER) {
            throw new Exception(MAX_POOL_NUMBER_EXCEED);
        }
        final ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        int indexStart = 0;
        List<Future<List<X>>> futures = new ArrayList<Future<List<X>>>();
        while (indexStart<list.size()) {
            int indexEnd = indexStart+batchSize;
            final List<T> subList = list.subList(indexStart, Math.min(indexEnd, list.size()));
            futures.add(executor.submit(new Callable<List<X>>() {
                public List<X> call() throws Exception {
                    return getObject(subList, x, otherParameter);
                }
            }));
            indexStart = indexEnd;
        }
        executor.shutdown();
        if (futures.size()!=0) {
            responseList = new ArrayList<X>();
            for (Future<List<X>> future : futures) {
                try {
                    if (future.get() != null) {
                        responseList.addAll(future.get());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return responseList;
    }

    protected abstract List<X> getObject(List<T> subList, Class<X> x, Object... otherParameter);
}
