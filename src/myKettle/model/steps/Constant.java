package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.constant.ConstantMeta;

/**
 * Created by shengyichen on 17-7-5.
 * 增加常量
 */
public class Constant implements MyStepMeta {
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        ConstantMeta meta = new ConstantMeta();
        meta.setDefault();
        meta.setFieldName(jsonObject.getString("fieldName").split(","));
        meta.setFieldType(jsonObject.getString("fieldType").split(","));
        meta.setFieldFormat(jsonObject.getString("fieldFormat").split(","));

        String[] fieldLength =jsonObject.getString("fieldLength").split(",");
        String[] fieldPrecision =jsonObject.getString("fieldPrecision").split(",");
        String[] empty =jsonObject.getString("emptyString").split(",");
        int[] i_length = new int[fieldLength.length];
        int[] i_precision = new int[fieldPrecision.length];
        boolean[] b_empty = new boolean[empty.length];

        for(int i=0;i<empty.length;i++){
            i_length[i]=Integer.valueOf(fieldLength[i]);
            i_precision[i]=Integer.valueOf(fieldPrecision[i]);
            b_empty[i]=Boolean.valueOf(empty[i]);
        }
        meta.setFieldLength(i_length);
        meta.setFieldPrecision(i_precision);
        meta.setEmptyString(b_empty);
        meta.setCurrency(jsonObject.getString("currency").split(","));
        meta.setDecimal(jsonObject.getString("decimal").split(","));
        meta.setGroup(jsonObject.getString("group").split(","));
        meta.setValue(jsonObject.getString("value").split(","));

        StepMeta stepMeta = new StepMeta(jsonObject.get("name").toString(),meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
        transMeta.addStep(stepMeta);
    }
}
