package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.uniquerowsbyhashset.UniqueRowsByHashSetMeta;

/**
 * Created by shengyichen on 17-7-5.
 * 唯一行 (哈希值)
 */
public class UniqueRowsByHashSet implements MyStepMeta {
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        UniqueRowsByHashSetMeta meta = new UniqueRowsByHashSetMeta();
        meta.setDefault();
        meta.setStoreValues(jsonObject.getBoolean("storeValues"));
        meta.setRejectDuplicateRow(jsonObject.getBoolean("rejectDuplicateRow"));
        meta.setErrorDescription(jsonObject.getString("errorDescription"));
        meta.setCompareFields(jsonObject.getString("compareFields").split(","));

        StepMeta stepMeta = new StepMeta(jsonObject.get("name").toString(),meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
        transMeta.addStep(stepMeta);
    }
}
