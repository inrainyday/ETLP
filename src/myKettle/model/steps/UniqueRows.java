package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.uniquerows.UniqueRowsMeta;

/**
 * Created by zhangzhimin on 5/27/17.
 */
public class UniqueRows implements MyStepMeta{
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        UniqueRowsMeta meta = new UniqueRowsMeta();
        meta.setDefault();


        StepMeta stepMeta = new StepMeta(jsonObject.getString("name"),meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
        transMeta.addStep(stepMeta);
    }
}
