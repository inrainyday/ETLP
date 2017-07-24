package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.randomvalue.RandomValueMeta;

/**
 * Created by shengyichen on 17-7-3.
 * 生成随机数
 */
public class RandomValue implements MyStepMeta {
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        RandomValueMeta meta = new RandomValueMeta();
        meta.setDefault();
        meta.setFieldName(jsonObject.getString("fileName").split(","));

        String[] s = jsonObject.getString("filedType").split(",");
        int[] type = new int[s.length];
        for(int i = 0;i<type.length;i++){
            type[i] = Integer.valueOf(s[i]);
        }
        meta.setFieldType(type);
        StepMeta stepMeta = new StepMeta(jsonObject.getString("name"),meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
        transMeta.addStep(stepMeta);
    }
}
