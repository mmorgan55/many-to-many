package edu.cnm.deepdive.manytomany;

import java.util.Date;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

  @Scheduled(fixedRate = 5000)
  public void currentTime() {
    System.out.println(new Date());
  }

}
