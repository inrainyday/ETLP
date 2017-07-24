package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.calculator.CalculatorMeta;
import org.pentaho.di.trans.steps.calculator.CalculatorMetaFunction;
import org.pentaho.di.trans.steps.regexeval.RegexEvalMeta;

/**
 * Created by shengyichen on 17-7-3.
 * 计算器
 */
public class Calculator implements MyStepMeta {
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        CalculatorMeta meta = new CalculatorMeta();
        meta.setDefault();
        CalculatorMetaFunction[] cmf = new CalculatorMetaFunction[jsonObject.length()];

        for(int i=0; i<jsonObject.length();i++){
            cmf[i].setFieldName(jsonObject.getString("fieldName").split(",")[i]);
            cmf[i].setCalcType(Integer.parseInt(jsonObject.getString("calcType").split(",")[i]));
        }
        meta.setCalculation(cmf);
        StepMeta stepMeta = new StepMeta(jsonObject.get("name").toString(),meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
        transMeta.addStep(stepMeta);
    }
}
