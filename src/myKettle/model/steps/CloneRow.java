package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.clonerow.CloneRowMeta;

/**
 * Created by shengyichen on 17-7-4.
 * 克隆行
 */
public class CloneRow implements MyStepMeta {
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        CloneRowMeta meta = new CloneRowMeta();
        meta.setDefault();
        meta.setNrClones(jsonObject.getString("nrClones"));
        meta.setAddCloneFlag(jsonObject.getBoolean("addCloneFlag"));
        meta.setCloneFlagField(jsonObject.getString("cloneFlagField"));
        meta.setNrCloneInField(jsonObject.getBoolean("nrCloneInField"));
        meta.setNrCloneField(jsonObject.getString("nrCloneField"));
        meta.setAddCloneNum(jsonObject.getBoolean("addCloneNum"));
        meta.setCloneNumField(jsonObject.getString("cloneNumField"));

        StepMeta stepMeta = new StepMeta(jsonObject.get("name").toString(),meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
        transMeta.addStep(stepMeta);
    }
}
