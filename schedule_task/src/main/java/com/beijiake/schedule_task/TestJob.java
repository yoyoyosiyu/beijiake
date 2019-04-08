package com.beijiake.schedule_task;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TestJob {


    @Scheduled(fixedDelay = 60000)
    void doJob() {
        Logger logger = LoggerFactory.getLogger(getClass());

        logger.info("task run!");
    }
}
