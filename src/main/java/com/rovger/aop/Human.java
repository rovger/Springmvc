package com.rovger.aop;

import org.springframework.stereotype.Service;

/**
 * Created by weijlu on 2017/5/9.
 */
@Service
public class Human implements Sleepable {
	public String sleep() {
		return "睡觉中。。。梦中自有颜如玉。";
	}
}
