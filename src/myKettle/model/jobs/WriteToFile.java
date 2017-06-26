package myKettle.model.jobs;

import myKettle.utils.KettleUtil;
import org.json.JSONObject;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.job.entries.writetofile.JobEntryWriteToFile;
import org.pentaho.di.job.entry.JobEntryCopy;

/**
 * Created by zhangzhimin on 6/2/17.
 * Write to file(写入文件)
 */
public class WriteToFile implements MyEntry{
    @Override
    public void setEntry(JobMeta jobMeta, JSONObject jsonObject) {
        JobEntryWriteToFile entry = new JobEntryWriteToFile();
        entry.setName(jsonObject.getString("name"));
        entry.setFilename(jsonObject.getString("filename"));
        entry.setEncoding(jsonObject.getString("encoding"));
        entry.setAppendFile(jsonObject.getBoolean("appendfile"));
        entry.setCreateParentFolder(jsonObject.getBoolean("createparentfolder"));
        entry.setContent(jsonObject.getString("content"));
        JobEntryCopy jobEntryCopy = new JobEntryCopy();
        jobEntryCopy.setEntry(entry);
        KettleUtil.drawEntry(entry,jobEntryCopy,jsonObject);
        jobMeta.addJobEntry(jobEntryCopy);
    }
}
