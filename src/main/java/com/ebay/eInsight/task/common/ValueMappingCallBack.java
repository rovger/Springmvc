package com.ebay.eInsight.task.common;

import java.util.List;

/**
 * Created by weijlu on 2017/6/23.
 */
public interface ValueMappingCallBack {

    public String callback(List<String> countBy);
    public List<String> reverseCallback(String callbackValue);

}
