package edu.cnm.deepdive.manytomany;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

  private static final Logger LOG = LoggerFactory.getLogger(ScheduledTasks.class);

  @Scheduled(fixedRate = 5000)
  public void currentTime() {
    LOG.info(new Date().toString());
    System.out.println(new Date());
  }

}
