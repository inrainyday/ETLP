package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.fuzzymatch.FuzzyMatchMeta;

/**
 * Created by shengyichen on 17-7-5.
 * 模糊匹配
 */
public class FuzzyMatch implements MyStepMeta{
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        FuzzyMatchMeta meta = new FuzzyMatchMeta();
        meta.setDefault();
        meta.setLookupField(jsonObject.getString("lookUpField"));
        meta.setMainStreamField(jsonObject.getString("mainStreamField"));
        meta.setAlgorithmType(jsonObject.getInt("algorithm"));
        meta.setCaseSensitive(jsonObject.getBoolean("caseSensitive"));
        meta.setGetCloserValue(jsonObject.getBoolean("closerValue"));
        meta.setMinimalValue(jsonObject.getString("minimaValue"));
        meta.setMaximalValue(jsonObject.getString("maximalValue"));
        meta.setSeparator(jsonObject.getString("separator"));
        meta.setOutputMatchField(jsonObject.getString("outputMatchField"));
        meta.setOutputValueField(jsonObject.getString("outputValueField"));
        meta.setValue(jsonObject.getString("value").split(","));
        meta.setValueName(jsonObject.getString("valueName").split(","));

        StepMeta stepMeta = new StepMeta(jsonObject.get("name").toString(),meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
        transMeta.addStep(stepMeta);
    }
}
