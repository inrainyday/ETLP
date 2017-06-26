package myKettle.test;

import myKettle.utils.KettleUtil;
import org.json.JSONObject;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.job.entries.deletefile.JobEntryDeleteFile;
import org.pentaho.di.job.entries.special.JobEntrySpecial;
import org.pentaho.di.job.entry.JobEntryCopy;

/**
 * Created by zhangzhimin on 6/1/17.
 */
public class KettleJobTest {
    public static void main(String[] args) throws Exception{
        KettleEnvironment.init();
        JobMeta jobMeta = new JobMeta();
//        JobEntryDeleteFile jobEntryDeleteFile = new JobEntryDeleteFile();
//        jobEntryDeleteFile.setName("delete a file");
//        JobEntrySpecial entry = new JobEntrySpecial();
//        entry.setName("START");
//        entry.setStart(true);
//        JobEntryCopy jobEntryCopy = new JobEntryCopy();
//        JobEntryCopy jobEntryCopy1 = new JobEntryCopy();
//        jobEntryCopy.setEntry(entry);
//        jobEntryCopy.setLocation(100, 100);
//        jobEntryCopy.setDrawn(true);
//        jobMeta.addJobEntry(jobEntryCopy);
//        jobEntryCopy1.setEntry(jobEntryDeleteFile);
//        jobEntryCopy1.setLocation(200,100);
//        jobEntryCopy1.setDrawn(true);
//        jobMeta.addJobEntry(jobEntryCopy1);



        JSONObject jo = new JSONObject("{\"type\":\"Start\",\"name\":\"INPUTTABLE_t_lzfx_base_syonline\",\"x\":\"200\",\"y\":\"200\"}");
        String b = KettleUtil.addEntry(jobMeta,jo);
//        String a = jobMeta.getXML();
        System.out.println(b);
    }
}
