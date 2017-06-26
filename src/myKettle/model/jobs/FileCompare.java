package myKettle.model.jobs;

import myKettle.utils.KettleUtil;
import org.json.JSONObject;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.job.entries.filecompare.JobEntryFileCompare;
import org.pentaho.di.job.entry.JobEntryCopy;

/**
 * Created by zhangzhimin on 6/2/17.
 * FileCompare(文件对比)
 */
public class FileCompare implements MyEntry{
    @Override
    public void setEntry(JobMeta jobMeta, JSONObject jsonObject) {
        JobEntryFileCompare entry = new JobEntryFileCompare();
        entry.setName(jsonObject.getString("name"));
        entry.setFilename1(jsonObject.getString("filename1"));
        entry.setFilename2(jsonObject.getString("filename2"));
        JobEntryCopy jobEntryCopy = new JobEntryCopy();
        jobEntryCopy.setEntry(entry);
        KettleUtil.drawEntry(entry,jobEntryCopy,jsonObject);
        jobMeta.addJobEntry(jobEntryCopy);
    }
}
