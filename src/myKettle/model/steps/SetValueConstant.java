package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.setvalueconstant.SetValueConstantMeta;

/**
 * Created by root on 17-7-6.
 * 将字段值设置为常量
 */
public class SetValueConstant implements MyStepMeta{
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        SetValueConstantMeta meta = new SetValueConstantMeta();
        meta.setDefault();
        meta.setUseVars(jsonObject.getBoolean("uservar"));
        meta.setFieldName(jsonObject.getString("fieldName").split(","));
        meta.setReplaceValue(jsonObject.getString("replaceValue").split(","));
        meta.setReplaceMask(jsonObject.getString("replaceMask").split(","));

        String[] empty =jsonObject.getString("emptyString").split(",");
        boolean[] b_empty = new boolean[empty.length];
        for(int i=0;i<empty.length;i++){
            b_empty[i]=Boolean.valueOf(empty[i]);
        }
        meta.setEmptyString(b_empty);

        StepMeta stepMeta = new StepMeta(jsonObject.get("name").toString(),meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
        transMeta.addStep(stepMeta);

    }
}
