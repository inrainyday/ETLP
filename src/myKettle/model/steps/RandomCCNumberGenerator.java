package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.randomccnumber.RandomCCNumberGeneratorMeta;

/**
 * Created by root on 17-7-3.
 * 生成随机的信用卡号
 */
public class RandomCCNumberGenerator implements MyStepMeta {
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        RandomCCNumberGeneratorMeta meta = new RandomCCNumberGeneratorMeta();
        meta.setDefault();
        meta.setCardNumberFieldName(jsonObject.getString("cardNumberFieldName"));
        meta.setCardTypeFieldName(jsonObject.getString("cardTypeFieldName"));
        meta.setCardLengthFieldName(jsonObject.getString("cardLengthFieldName"));

        meta.setFieldCCType(jsonObject.getString("fieldCCType").split(","));
        meta.setFieldCCLength(jsonObject.getString("fieldCCLength").split(","));
        meta.setFieldCCSize(jsonObject.getString("fieldCCSize").split(","));
        StepMeta stepMeta = new StepMeta(jsonObject.get("name").toString(),meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
        transMeta.addStep(stepMeta);
    }
}
