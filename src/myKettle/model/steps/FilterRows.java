package myKettle.model.steps;

import org.json.JSONArray;
import org.json.JSONObject;
import org.pentaho.di.core.Condition;
import org.pentaho.di.core.row.ValueMetaAndData;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.filterrows.FilterRowsMeta;

/**
 * Created by shengyichen on 17-7-5.
 * 过滤记录
 *
 */
public class FilterRows implements MyStepMeta {
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        FilterRowsMeta meta = new FilterRowsMeta();
        meta.setDefault();

        meta.setConditionXML(jsonObject.getString("condition"));
        StepMeta stepMeta = new StepMeta(jsonObject.get("name").toString(),meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
        transMeta.addStep(stepMeta);
    }
}
