package myKettle.model.jobs;

import myKettle.utils.KettleUtil;
import org.json.JSONObject;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.job.entries.ping.JobEntryPing;
import org.pentaho.di.job.entry.JobEntryCopy;

/**
 * Created by zhangzhimin on 6/2/17.
 * Ping
 */
public class Ping implements MyEntry{
    @Override
    public void setEntry(JobMeta jobMeta, JSONObject jsonObject) {
        JobEntryPing entry = new JobEntryPing();
        entry.setName(jsonObject.getString("name"));
        entry.setHostname(jsonObject.getString("hostname"));
        JobEntryCopy jobEntryCopy = new JobEntryCopy();
        jobEntryCopy.setEntry(entry);
        KettleUtil.drawEntry(entry,jobEntryCopy,jsonObject);
        jobMeta.addJobEntry(jobEntryCopy);
    }
}
