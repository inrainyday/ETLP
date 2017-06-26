package myKettle.model.jobs;

import myKettle.utils.KettleUtil;
import org.json.JSONObject;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.job.entries.createfolder.JobEntryCreateFolder;
import org.pentaho.di.job.entry.JobEntryCopy;

/**
 * Created by zhangzhimin on 6/2/17.
 * CreateFolder(创建目录)
 */
public class CreateFolder implements MyEntry{
    @Override
    public void setEntry(JobMeta jobMeta, JSONObject jsonObject) {
        JobEntryCreateFolder entry = new JobEntryCreateFolder();
        entry.setName(jsonObject.getString("name"));
        entry.setFoldername(jsonObject.getString("foldername"));
        JobEntryCopy jobEntryCopy = new JobEntryCopy();
        KettleUtil.drawEntry(entry,jobEntryCopy,jsonObject);
        jobMeta.addJobEntry(jobEntryCopy);
    }


}
