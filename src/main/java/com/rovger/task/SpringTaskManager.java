package com.rovger.task;

import com.rovger.task.work.Task1;
import com.rovger.task.work.Task2;
import com.rovger.task.work.Task3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by weijlu on 2017/4/1.
 */
@Service
public class SpringTaskManager {

	@Autowired
	@Qualifier("springTask")
	private TaskScheduler scheduler;

	@PostConstruct
	public void schedule() {
		scheduler.schedule(new Task1(), new CronTrigger("15 * * * * *"));
		scheduler.schedule(new Task2(), new CronTrigger("30 * * * * *"));
		scheduler.schedule(new Task3(), new CronTrigger("45 * * * * *"));
	}

}
