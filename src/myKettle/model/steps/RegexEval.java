package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.regexeval.RegexEvalMeta;

/**
 * Created by zhangzhimin on 6/9/17.
 */
public class RegexEval implements MyStepMeta{
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        RegexEvalMeta meta = new RegexEvalMeta();
        meta.setDefault();
        StepMeta stepMeta = new StepMeta(jsonObject.getString("name"),meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
        transMeta.addStep(stepMeta);
        meta.getRegexOptions();
    }
}
