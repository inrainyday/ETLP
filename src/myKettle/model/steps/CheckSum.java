package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.checksum.CheckSumMeta;

/**
 * Created by shengyichen on 17-7-5.
 * 增加校验列
 */
public class CheckSum implements MyStepMeta {
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        CheckSumMeta meta = new CheckSumMeta();
        meta.setDefault();
        meta.setCheckSumType(jsonObject.getString("checkSumType"));
        meta.setResultType(jsonObject.getInt("resultType"));
        meta.setResultFieldName(jsonObject.getString("resultFieldName"));
        meta.setCompatibilityMode(jsonObject.getBoolean("compatibilityMode"));
        meta.setFieldName(jsonObject.getString("fieldName").split(","));

        StepMeta stepMeta = new StepMeta(jsonObject.get("name").toString(),meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
        transMeta.addStep(stepMeta);
    }
}
