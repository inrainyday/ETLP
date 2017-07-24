package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.addsequence.AddSequenceMeta;

/**
 * Created by shengyichen on 17-7-5.
 * 增加序列
 */
public class AddSequence implements MyStepMeta {
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        AddSequenceMeta meta = new AddSequenceMeta();
        meta.setDefault();
        meta.setValuename(jsonObject.getString("valueName"));
        meta.setUseDatabase(jsonObject.getBoolean("useDatebase"));
        DatabaseMeta databaseMeta = transMeta.findDatabase(jsonObject.getString("db"));
        meta.setDatabase(databaseMeta);
        meta.setSchemaName(jsonObject.getString("schemaName"));
        meta.setSequenceName(jsonObject.getString("sequenceName"));
        meta.setUseCounter(jsonObject.getBoolean("useCounter"));
        meta.setCounterName(jsonObject.getString("counterName"));
        meta.setStartAt(jsonObject.getString("startAt"));
        meta.setIncrementBy(jsonObject.getString("incrementBy"));
        meta.setMaxValue(jsonObject.getString("maxValue"));

        StepMeta stepMeta = new StepMeta(jsonObject.get("name").toString(),meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
        transMeta.addStep(stepMeta);
    }
}
