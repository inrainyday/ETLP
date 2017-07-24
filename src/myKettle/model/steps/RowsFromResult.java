package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.rowsfromresult.RowsFromResultMeta;

/**
 * Created by shengyichen on 17-7-5.
 * 从结果获取记录
 */
public class RowsFromResult implements MyStepMeta {
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        RowsFromResultMeta meta = new RowsFromResultMeta();
        meta.setDefault();
        String[] fieldname = jsonObject.getString("fieldName").split(",");
        String[] type = jsonObject.getString("type").split(",");
        String[] length = jsonObject.getString("length").split(",");
        String[] precision = jsonObject.getString("precision").split(",");

        int[] type_int = new int[type.length];
        int[] length_int = new int[length.length];
        int[] precision_int = new int[precision.length];
        for(int i = 0;i<type.length;i++){
            type_int[i] = Integer.valueOf(type[i]);
            length_int[i] = Integer.valueOf(length[i]);
            precision_int[i] = Integer.valueOf(precision[i]);
        }
        meta.setType(type_int);
        meta.setLength(length_int);
        meta.setPrecision(precision_int);

        StepMeta stepMeta = new StepMeta(jsonObject.get("name").toString(),meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
        transMeta.addStep(stepMeta);
    }
}
