package com.imanafrica;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@EnableScheduling
@Component
public class ScheduledTasks {
	Logger log = Logger.getLogger(ScheduledTasks.class);
	
	@Scheduled(fixedRate=60000)
	public void displayTime(){
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		log.info("The current time is: " + format.format(new Date()));
		
	}

}
