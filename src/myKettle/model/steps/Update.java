package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.update.UpdateMeta;

/**
 * Created by zhangzhimin on 5/25/17.
 * Update(更新)
 */
public class Update implements MyStepMeta{
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        UpdateMeta meta = new UpdateMeta();
        meta.setDefault();
        meta.setDatabaseMeta(transMeta.findDatabase(jsonObject.getString("db")));
        meta.setTableName(jsonObject.getString("tablename"));
        meta.setCommitSize(jsonObject.getInt("commitsize"));
        //用来查询的关键字
        //表字段
        meta.setKeyLookup(jsonObject.getString("keylookup").split(","));
        //比较符
        meta.setKeyCondition(jsonObject.getString("keycondition").split(","));
        //流里的字段1
        meta.setKeyStream(jsonObject.getString("keystream").split(","));
        //流里的字段2
        meta.setKeyStream2(null);
        //更新字段
        //表字段
        meta.setUpdateLookup(jsonObject.getString("updatelookup").split(","));
        //流里的字段
        meta.setUpdateStream(jsonObject.getString("updatestream").split(","));
        StepMeta stepMeta = new StepMeta(jsonObject.getString("name"),meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
        transMeta.addStep(stepMeta);
    }
}
