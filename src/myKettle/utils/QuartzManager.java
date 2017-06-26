//package myKettle.utils;
//
//import java.text.ParseException;
//
//import org.quartz.*;
//import org.quartz.impl.StdSchedulerFactory;
//
///**
//* Created by zhangzhimin on 6/14/17.
//*/
//public class QuartzManager {
//    private static SchedulerFactory sf = new StdSchedulerFactory();
//    private static String JOB_GROUP_NAME = "group1";
//    private static String TRIGGER_GROUP_NAME = "trigger1";
//
//
//    /** *//**
//     *  添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
//     * @param jobName 任务名
//     * @param job     任务
//     * @param time    时间设置，参考quartz说明文档
//     * @throws SchedulerException
//     * @throws ParseException
//     */
//    public static void addJob(Class<? extends Job> jobClas,String jobName,Job job,String time)
//            throws SchedulerException, ParseException{
//        Scheduler sched = sf.getScheduler();
//        JobDetail jd = JobBuilder.newJob(jobClas).withIdentity(jobName, JOB_GROUP_NAME).build();
//        JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME, job.getClass());//任务名，任务组，任务执行类
//        //触发器
//        CronTrigger  trigger =
//                new CronTrigger(jobName, TRIGGER_GROUP_NAME);//触发器名,触发器组
//        TriggerBuilder.newTrigger();
//        trigger.setCronExpression(time);//触发器时间设定
//        sched.scheduleJob(jobDetail,trigger);
//        //启动
//        if(!sched.isShutdown())
//            sched.start();
//    }
//
//    /** *//**
//     * 添加一个定时任务
//     * @param jobName 任务名
//     * @param jobGroupName 任务组名
//     * @param triggerName  触发器名
//     * @param triggerGroupName 触发器组名
//     * @param job     任务
//     * @param time    时间设置，参考quartz说明文档
//     * @throws SchedulerException
//     * @throws ParseException
//     */
//    public static void addJob(String jobName,String jobGroupName,
//                              String triggerName,String triggerGroupName,
//                              Job job,String time)
//            throws SchedulerException, ParseException{
//        Scheduler sched = sf.getScheduler();
//        JobDetail jobDetail = new JobDetail(jobName, jobGroupName, job.getClass());//任务名，任务组，任务执行类
//        //触发器
//        CronTrigger  trigger =
//                new CronTrigger(triggerName, triggerGroupName);//触发器名,触发器组
//        trigger.setCronExpression(time);//触发器时间设定
//        sched.scheduleJob(jobDetail,trigger);
//        if(!sched.isShutdown())
//            sched.start();
//    }
//
//    /** *//**
//     * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
//     * @param jobName
//     * @param time
//     * @throws SchedulerException
//     * @throws ParseException
//     */
//    public static void modifyJobTime(String jobName,String time)
//            throws SchedulerException, ParseException{
//        Scheduler sched = sf.getScheduler();
//        Trigger trigger =  sched.getTrigger(jobName,TRIGGER_GROUP_NAME);
//        if(trigger != null){
//            CronTrigger  ct = (CronTrigger)trigger;
//            ct.setCronExpression(time);
//            sched.resumeTrigger(jobName,TRIGGER_GROUP_NAME);
//        }
//    }
//
//    /** *//**
//     * 修改一个任务的触发时间
//     * @param triggerName
//     * @param triggerGroupName
//     * @param time
//     * @throws SchedulerException
//     * @throws ParseException
//     */
//    public static void modifyJobTime(String triggerName,String triggerGroupName,
//                                     String time)
//            throws SchedulerException, ParseException{
//        Scheduler sched = sf.getScheduler();
//        TriggerKey triggerKey = new TriggerKey(triggerName,triggerGroupName);
//        Trigger trigger =  sched.getTrigger(triggerKey);
//        if(trigger != null){
//            CronTrigger  ct = (CronTrigger)trigger;
//            //修改时间
//            ct.setCronExpression(time);
//            //重启触发器
//            sched.resumeTrigger(triggerKey);
//        }
//    }
//
//    /** *//**
//     * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
//     * @param jobName
//     * @throws SchedulerException
//     */
//    public static void removeJob(String jobName)
//            throws SchedulerException{
//        TriggerKey triggerKey = new TriggerKey(jobName,TRIGGER_GROUP_NAME);
//        Scheduler sched = sf.getScheduler();
//        sched.pauseTrigger(triggerKey);//停止触发器
//        sched.unscheduleJob(triggerKey);//移除触发器
//        JobKey jobKey = new JobKey(jobName,JOB_GROUP_NAME);
//        sched.deleteJob(jobKey);//删除任务
//    }
//
//    /** *//**
//     * 移除一个任务
//     * @param jobName
//     * @param jobGroupName
//     * @param triggerName
//     * @param triggerGroupName
//     * @throws SchedulerException
//     */
//    public static void removeJob(String jobName,String jobGroupName,
//                                 String triggerName,String triggerGroupName)
//            throws SchedulerException{
//        Scheduler sched = sf.getScheduler();
//        TriggerKey triggerKey = new TriggerKey(triggerName,triggerGroupName);
//        JobKey jobKey = new JobKey(jobName,jobGroupName);
//        sched.pauseTrigger(triggerKey);//停止触发器
//        sched.unscheduleJob(triggerKey);//移除触发器
//        sched.deleteJob(jobKey);//删除任务
//    }
//}
