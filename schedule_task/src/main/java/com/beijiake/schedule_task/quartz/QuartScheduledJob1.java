package com.beijiake.schedule_task.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QuartScheduledJob1 {

    public void execute() {
        System.out.println("quartz ScheduledJob1 Task:" + dateFormat().format(new Date()));
    }

    private SimpleDateFormat dateFormat() {
        return new SimpleDateFormat("HH:mm:ss");
    }
}
