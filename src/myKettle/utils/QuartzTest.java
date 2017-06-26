package myKettle.utils;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * Created by zhangzhimin on 6/15/17.
 */
public class QuartzTest implements Job{
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        System.out.println(new Date().toString()+"     "+dataMap.get("kettleName"));
    }
}
