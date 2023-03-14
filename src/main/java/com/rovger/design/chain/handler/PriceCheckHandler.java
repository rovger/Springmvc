package com.rovger.design.chain.handler;

import com.rovger.design.chain.ErrorCode;
import com.rovger.design.chain.ProductVO;
import com.rovger.design.chain.Result;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @Description: 价格校验处理器
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2023年02月27日 15:32
 */
@Component
public class PriceCheckHandler extends AbstractCheckHandler{
    /**
     * 处理器执行方法
     *
     * @param param
     * @return
     */
    @Override
    public Result handle(ProductVO param) {
        System.out.println("价格校验 Handler 开始...");

        //非法价格校验
        boolean illegalPrice =  param.getPrice().compareTo(BigDecimal.ZERO) <= 0;
        if (illegalPrice) {
            return Result.failure(ErrorCode.PARAM_PRICE_ILLEGAL_ERROR);
        }
        //其他校验逻辑...

        System.out.println("价格校验 Handler 通过...");

        //执行下一个处理器
        return super.next(param);
    }
}
