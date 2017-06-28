package myKettle.utils;

import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;
/**
 * Created by zhangzhimin on 6/14/17.
 * quartz调度程序
 */
public class QuartzUtil {
    private final static String JOB_GROUP_NAME = "QUARTZ_JOBGROUP_NAME";//任务组
    private final static String TRIGGER_GROUP_NAME = "QUARTZ_TRIGGERGROUP_NAME";//触发器组
    private static SchedulerFactory sf = new StdSchedulerFactory();
    private static Logger log = Logger.getLogger(QuartzUtil.class);//日志

    /**
     * 添加任务的方法
     * @param jobName  任务名
     * @param triggerName  触发器名
     * @param jobClass  执行任务的类
     * @param seconds  间隔时间
     * @throws SchedulerException
     */
    public static void addJob(String jobName,String triggerName,Class<? extends Job> jobClass,int seconds) throws SchedulerException{
        log.info("==================initialization=================");

        //创建一个SchedulerFactory工厂实例
//        SchedulerFactory sf = new StdSchedulerFactory();

        //通过SchedulerFactory构建Scheduler对象
        Scheduler sche = sf.getScheduler();

        log.info("===================initialize finshed===================");

        log.info("==============add the Job to Scheduler==================");

        //用于描叙Job实现类及其他的一些静态信息，构建一个作业实例
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobName, JOB_GROUP_NAME)
                .build();

        //构建一个触发器，规定触发的规则
        Trigger trigger = TriggerBuilder.newTrigger()//创建一个新的TriggerBuilder来规范一个触发器
                .withIdentity(triggerName, TRIGGER_GROUP_NAME)//给触发器起一个名字和组名
                .startNow()//立即执行
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(seconds)//时间间隔  单位：秒
                                .repeatForever()//一直执行
                )
                .build();//产生触发器
        //向Scheduler中添加job任务和trigger触发器
        sche.scheduleJob(jobDetail, trigger);
        //启动
        sche.start();
    }


    /**
     * 添加任务的方法
     * @param jobName  任务名
     * @param triggerName  触发器名
     * @param jobClass  执行任务的类
     * @param cronSchedule  Cron表达式
     * @param kettleName  要添加调度的作业/转换名称
     * @throws org.quartz.SchedulerException
     */
    public static void addCronJob(String jobName,String triggerName,Class<? extends Job> jobClass
            ,String cronSchedule,String kettleName) throws SchedulerException{
        log.info("==================initialization=================");

        //创建一个SchedulerFactory工厂实例

        //通过SchedulerFactory构建Scheduler对象
        Scheduler sche = sf.getScheduler();

        log.info("===================initialize finshed===================");

        log.info("==============add the Job to Scheduler==================");

        //用于描叙Job实现类及其他的一些静态信息，构建一个作业实例
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobName, null)
                .build();
        //将参数保存在jobDetail中
        jobDetail.getJobDataMap().put("kettleName",kettleName);

        //构建一个Cron触发器，规定触发的规则
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName, null).startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(cronSchedule))
                .build();
        //向Scheduler中添加job任务和trigger触发器
        sche.scheduleJob(jobDetail, cronTrigger);
        //启动
        sche.start();
    }
    /**
     * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
     * @param jobName
     * @param triggerName
     * @param cronSchedule
     * @throws SchedulerException
     * @throws ParseException
     */
    public static void modifyJob(String jobName,String triggerName,String cronSchedule) throws SchedulerException,ParseException{
        Scheduler sched = sf.getScheduler();
        TriggerKey triggerKey = new TriggerKey(triggerName);
        CronTrigger trigger = (CronTrigger)sched.getTrigger(triggerKey);
        if(trigger != null){
            trigger = TriggerBuilder.newTrigger().withIdentity(triggerName).startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronSchedule)).build();
            sched.rescheduleJob(triggerKey,trigger);
        }

    }
    /**
     * 移除任务的方法
     * @param jobName  任务名
     * @param triggerName  触发器名
     */
    public static void removeJob(String jobName,String triggerName) throws Exception{
        TriggerKey triggerKey = new TriggerKey(triggerName);
        JobKey jobKey = new JobKey(jobName);
        Scheduler sched = sf.getScheduler();
        String a = sched.getSchedulerName();
        System.out.println(a);
        String b = "********** "+sched.getJobDetail(jobKey).toString();
        System.out.println(b);
        sched.pauseTrigger(triggerKey);//停止触发器
        sched.unscheduleJob(triggerKey);//移除触发器
//        JobKey jobKey = new JobKey(jobName,JOB_GROUP_NAME);
        sched.deleteJob(jobKey);//删除任务

    }
    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        try {
            String kettleName = "abcd";
            addCronJob("job1","tg1",QuartzTest.class,"*/5 * * * * ?",kettleName);//每5秒一次
            Thread.sleep(11000);
            System.out.println("stopped");
            modifyJob("job1","tg1","*/2 * * * * ?");
//            removeJob("job1", "tg1");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
