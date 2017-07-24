package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.stringcut.StringCutMeta;

/**
 * Created by shengyichen on 17-7-4.
 */
public class StringCut implements MyStepMeta{

    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        StringCutMeta meta = new StringCutMeta();
        meta.setDefault();
        meta.setFieldInStream(jsonObject.getString("fieldInStream").split(","));
        meta.setFieldOutStream(jsonObject.getString("fieldOutStream").split(","));
        meta.setCutFrom(jsonObject.getString("cutFrom").split(","));
        meta.setCutTo(jsonObject.getString("cutTo").split(","));
        StepMeta stepMeta = new StepMeta(jsonObject.get("name").toString(),meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
        transMeta.addStep(stepMeta);
    }
}
