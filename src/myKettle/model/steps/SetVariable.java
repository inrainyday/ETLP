package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.setvariable.SetVariableMeta;
import myKettle.utils.JsonBinder;

/**
 * Created by shengyichen on 17-7-5.
 * 设置变量
 */
public class SetVariable implements MyStepMeta {
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        SetVariableMeta meta = new SetVariableMeta();
        meta.setDefault();
        meta.setUsingFormatting(jsonObject.getBoolean("usingformatting"));
        meta.setFieldName(jsonObject.getString("fieldname").split(","));
        meta.setVariableName(jsonObject.getString("variablename").split(","));
        //meta.setVariableType(JsonBinder.IntArrayTrans(jsonObject.getString("varibletype")));

        StepMeta stepMeta = new StepMeta(jsonObject.get("name").toString(),meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
        transMeta.addStep(stepMeta);
    }
}
