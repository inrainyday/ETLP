package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.numberrange.NumberRangeMeta;
import org.pentaho.di.trans.steps.numberrange.NumberRangeRule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shengyichen on 17-7-4.
 * 数值范围
 */
public class NumberRange implements MyStepMeta {
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        NumberRangeMeta meta = new NumberRangeMeta();
        meta.setDefault();
        meta.setInputField(jsonObject.getString("inputField"));
        meta.setOutputField(jsonObject.getString("outputField"));
        meta.setFallBackValue(jsonObject.getString("fallBackValue"));

        String[] lowerBound = jsonObject.getString("lowerBound").split(",");
        String[] upperBound = jsonObject.getString("upperBound").split(",");
        String[] value = jsonObject.getString("value").split(",");
        List<NumberRangeRule> nrr = new ArrayList<>(lowerBound.length);
        NumberRangeRule n=null;
        for (int i = 0 ;i< lowerBound.length; i++){
            n = new NumberRangeRule(Double.valueOf(lowerBound[i]),Double.valueOf(upperBound[i]),value[i]);
            nrr.set(i,n);
        }
        meta.setRules(nrr);

        StepMeta stepMeta = new StepMeta(jsonObject.get("name").toString(),meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
        transMeta.addStep(stepMeta);
    }
}
