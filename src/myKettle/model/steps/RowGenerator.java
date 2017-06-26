package myKettle.model.steps;

import myKettle.utils.KettleUtil;
import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.rowgenerator.RowGeneratorMeta;



/**
 * Created by zhangzhimin on 5/15/17.
 * RowGenerator(生成记录)
 */
public class RowGenerator implements MyStepMeta{
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        RowGeneratorMeta meta = new RowGeneratorMeta();
        meta.setDefault();
        System.out.println(meta.getXML());
        String[] fieldnames = KettleUtil.format(jsonObject.getString("fieldname"));
        String[] fieldtypes = KettleUtil.format(jsonObject.getString("fieldtype"));
//        String[] fieldformats = KettleUtil.format(jsonObject.getString("fieldformat"));
        String[] values = KettleUtil.format(jsonObject.getString("value"));
//        System.out.println(fieldformats.length);
        meta.setFieldName(fieldnames);
        meta.setValue(values);
        int[] i = {-1,-1};
        meta.setFieldPrecision(i);
        meta.setFieldLength(i);
        boolean[] b = {false,false};
        meta.setEmptyString(b);
        meta.setFieldType(fieldtypes);

        StepMeta stepMeta = new StepMeta(jsonObject.getString("name"),meta);
        stepMeta.setLocation(jsonObject.getInt("x"), jsonObject.getInt("y"));
        stepMeta.drawStep();
        System.out.println(meta.getXML());
        transMeta.addStep(stepMeta);
    }
}
