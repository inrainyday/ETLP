package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepIOMetaInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.mergerows.MergeRowsMeta;
import org.pentaho.di.trans.steps.synchronizeaftermerge.SynchronizeAfterMergeMeta;

/**
 * Created by zhangzhimin on 5/16/17.
 * MergeRows(合并记录)
 *
 */
public class MergeRows implements MyStepMeta{
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {

        MergeRowsMeta meta = new MergeRowsMeta();
        StepIOMetaInterface stepIOMetaInterface = meta.getStepIOMeta();
        stepIOMetaInterface.getInfoStreams().get(0).setStepMeta(transMeta.findStep(jsonObject.getString("old")));
        stepIOMetaInterface.getInfoStreams().get(1).setStepMeta(transMeta.findStep(jsonObject.getString("new")));
        meta.setFlagField(jsonObject.getString("flag")); //设置标志字段
        meta.setKeyFields(jsonObject.getString("key").split(","));
        meta.setValueFields(jsonObject.getString("value").split(","));
        StepMeta stepMeta = new StepMeta(jsonObject.getString("name"), meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
        transMeta.addStep(stepMeta);

    }
}
