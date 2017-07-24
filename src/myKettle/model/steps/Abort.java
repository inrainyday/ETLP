package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.abort.AbortMeta;

/**
 * Created by shengyichen on 17-7-5.
 * 中止
 */
public class Abort implements MyStepMeta{
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        AbortMeta meta = new AbortMeta();
        meta.setDefault();
        meta.setRowThreshold(jsonObject.getString("rowThreshold"));
        meta.setMessage(jsonObject.getString("message"));
        meta.setAlwaysLogRows(jsonObject.getBoolean("alwaysLogRows"));

        StepMeta stepMeta = new StepMeta(jsonObject.get("name").toString(), meta);
        stepMeta.setLocation(jsonObject.getInt("x"), jsonObject.getInt("y"));
        stepMeta.drawStep();
        transMeta.addStep(stepMeta);
    }
}
