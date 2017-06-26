package myKettle.model.jobs;

import myKettle.utils.KettleUtil;
import org.json.JSONObject;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.job.entries.special.JobEntrySpecial;
import org.pentaho.di.job.entry.JobEntryCopy;

/**
 * Created by zhangzhimin on 6/1/17.
 */
public class Start implements  MyEntry{
    @Override
    public void setEntry(JobMeta jobMeta, JSONObject jsonObject) {
        JobEntrySpecial entry = new JobEntrySpecial();
        entry.setStart(true);
        entry.setName(jsonObject.getString("name"));
        JobEntryCopy jobEntryCopy = new JobEntryCopy();
//        jobEntryCopy.setEntry(entry);
//        jobEntryCopy.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
//        jobEntryCopy.setDrawn(true);
        KettleUtil.drawEntry(entry,jobEntryCopy,jsonObject);
        jobMeta.addJobEntry(jobEntryCopy);
    }
}
