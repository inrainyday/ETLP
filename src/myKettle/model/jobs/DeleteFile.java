package myKettle.model.jobs;

import myKettle.utils.KettleUtil;
import org.json.JSONObject;
import org.pentaho.di.core.annotations.JobEntry;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.job.entries.deletefile.JobEntryDeleteFile;
import org.pentaho.di.job.entry.JobEntryCopy;

/**
 * Created by zhangzhimin on 6/1/17.
 */
public class DeleteFile implements MyEntry{
    @Override
    public void setEntry(JobMeta jobMeta, JSONObject jsonObject) {
        JobEntryDeleteFile entry = new JobEntryDeleteFile();
        entry.setName(jsonObject.getString("name"));
        entry.setFilename(jsonObject.getString("filename"));
        entry.setFailIfFileNotExists(false);
        JobEntryCopy jobEntryCopy = new JobEntryCopy();
        //设置坐标并显示
        KettleUtil.drawEntry(entry,jobEntryCopy,jsonObject);
        //添加作业项
        jobMeta.addJobEntry(jobEntryCopy);
    }
}
