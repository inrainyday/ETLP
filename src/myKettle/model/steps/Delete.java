package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.delete.DeleteMeta;

/**
 * Created by zhangzhimin on 5/25/17.
 * Delete(删除)
 */
public class Delete implements MyStepMeta{
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        DeleteMeta meta = new DeleteMeta();
        meta.setDefault();
        DatabaseMeta databaseMeta = transMeta.findDatabase(jsonObject.getString("db"));
        meta.setDatabaseMeta(databaseMeta);
        meta.setTableName(jsonObject.getString("tb"));
        meta.setCommitSize(jsonObject.getInt("size"));
        meta.setKeyStream(jsonObject.getString("keystream").split(","));
        meta.setKeyStream2(new String[]{""});
        meta.setKeyCondition(jsonObject.getString("keycondition").split(","));
        meta.setKeyCondition(jsonObject.getString("").split(","));
        StepMeta stepMeta = new StepMeta(jsonObject.get("name").toString(),meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
        transMeta.addStep(stepMeta);
    }
}
