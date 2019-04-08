package com.beijiake.schedule_task.quartz;


import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzSchedule {

    @Bean
    public QuartScheduledJob1 getScheduledJob() {
        return new QuartScheduledJob1();
    }

    @Bean(name="scheduler")
    public SchedulerFactoryBean schedulerFactoryBean(Trigger trigger) {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();

        // 用于quartz集群,QuartzScheduler 启动时更新己存在的Job
        factoryBean.setOverwriteExistingJobs(true);

        // 延时启动，应用启动1秒后
        factoryBean.setStartupDelay(1);

        factoryBean.setTriggers(trigger);

        return factoryBean;

    }

    @Bean(name= "jobTrigger")
    public CronTriggerFactoryBean cronTriggerFactoryBean(JobDetail jobDetail) {
        CronTriggerFactoryBean triggerFactoryBean = new CronTriggerFactoryBean();

        triggerFactoryBean.setJobDetail(jobDetail);

        // cron表达式，每1分钟执行一次
        triggerFactoryBean.setCronExpression("0/60 * * * * ?");
        triggerFactoryBean.setBeanName("jobTrigger");

        return triggerFactoryBean;
    }

    @Bean
    public MethodInvokingJobDetailFactoryBean detailFactoryBean(QuartScheduledJob1 scheduledJob1) {
        MethodInvokingJobDetailFactoryBean jobDetail =  new MethodInvokingJobDetailFactoryBean();

        // 是否并发执行
        jobDetail.setConcurrent(false);

        // 设置任务的名字
        jobDetail.setName("jobDetail");

        // 设置任务的分组，在多任务的时候使用
        jobDetail.setGroup("jobDetailGroup");

        // 需要执行的对象
        jobDetail.setTargetObject(scheduledJob1);

        // 需要执行的对象的方法名
        jobDetail.setTargetMethod("execute");

        return jobDetail;
    }
}
