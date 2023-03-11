package com.rovger.transaction;

import org.apache.ibatis.transaction.Transaction;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author weijlu
 * @version 1.0
 * @description Spring事务异步回调
 * @date 2022/12/24 20:03:38
 */
public class TransactionUtil {

    @Transactional
    public void doTx() {
        // start tx

        // 只有事务成功执行后，才会执行回调操作
        TransactionUtil.doAfterTransaction(new DoTransactionCompleted(() -> {
            // send MQ... RPC...
        }));

        // end tx
    }

    public static void doAfterTransaction(DoTransactionCompleted doTransactionCompleted) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(doTransactionCompleted);
        }
    }
}

class DoTransactionCompleted implements TransactionSynchronization {

    private Runnable runnable;

    public DoTransactionCompleted(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void afterCompletion(int status) {
        if (status == TransactionSynchronization.STATUS_COMMITTED) {
            this.runnable.run();
        }
    }
}
