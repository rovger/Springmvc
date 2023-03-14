package com.rovger.design.chain.handler;

import com.rovger.design.chain.ErrorCode;
import com.rovger.design.chain.ProductVO;
import com.rovger.design.chain.Result;
import org.springframework.stereotype.Component;

/**
 * @Description: 库存校验处理器
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2023年02月27日 15:34
 */
@Component
public class StockCheckHandler extends AbstractCheckHandler {
    /**
     * 处理器执行方法
     *
     * @param param
     * @return
     */
    @Override
    public Result handle(ProductVO param) {
        System.out.println("库存校验 Handler 开始...");

        //非法库存校验
        boolean illegalStock = param.getStock() < 0;
        if (illegalStock) {
            return Result.failure(ErrorCode.PARAM_STOCK_ILLEGAL_ERROR);
        }
        //其他校验逻辑..

        System.out.println("库存校验 Handler 通过...");

        //执行下一个处理器
        return super.next(param);
    }
}
