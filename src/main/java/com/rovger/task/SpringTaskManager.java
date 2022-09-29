package com.rovger.task;

import org.springframework.stereotype.Service;

/**
 * Created by weijlu on 2017/4/1.
 */
@Service
public class SpringTaskManager {
    //由于Dubbo的启动，暂时停止spring schedule的任务
	/*@Autowired
	@Qualifier("springTask")
	private TaskScheduler scheduler;

	@PostConstruct
	public void schedule() {
		scheduler.schedule(new Task1(), new CronTrigger("15 * * * * *"));
		scheduler.schedule(new Task2(), new CronTrigger("30 * * * * *"));
		scheduler.schedule(new Task3(), new CronTrigger("45 * * * * *"));
	}*/

}
