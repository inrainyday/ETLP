package myKettle.model.jobs;

import org.json.JSONObject;
import org.pentaho.di.job.JobMeta;

/**
 * Created by lvyang on 5/10/17.
 */
public interface MyEntry {
    public void setEntry(JobMeta jobMeta, JSONObject jsonObject);
}
