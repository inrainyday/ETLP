package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepListener;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaChangeListenerInterface;
import org.pentaho.di.trans.steps.accessoutput.AccessOutputMeta;

/**
 * Created by shengyichen on 17-6-30.
 * Access 输出
 */
public class AccessOutput implements MyStepMeta{

    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        AccessOutputMeta meta = new AccessOutputMeta();
        meta.setDefault();
        meta.setFilename(jsonObject.getString("fileName"));
        meta.setDoNotOpenNewFileInit(jsonObject.getBoolean("openNewFileInit"));
        //create datebase
        meta.setFileCreated(jsonObject.getBoolean("fileCreated"));
        meta.setTablename(jsonObject.getString("tableName"));
        //create table
        meta.setTableCreated(jsonObject.getBoolean("tableCreated"));
        meta.setTableTruncated(jsonObject.getBoolean("tableTruncared"));
        meta.setCommitSize(jsonObject.getInt("commitSize"));
        meta.setAddToResultFiles(jsonObject.getBoolean("addToResultFiles"));
        StepMeta stepMeta = new StepMeta(jsonObject.getString("name"),meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
        //stepMeta.setCopies();
        transMeta.addStep(stepMeta);
    }
}
