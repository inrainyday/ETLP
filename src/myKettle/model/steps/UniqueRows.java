package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.uniquerows.UniqueRowsMeta;

/**
 * Created by zhangzhimin on 5/27/17.
 * 去除重复记录
 */
public class UniqueRows implements MyStepMeta{
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        UniqueRowsMeta meta = new UniqueRowsMeta();
        meta.setDefault();
        meta.setCountRows(jsonObject.getBoolean("countRows"));
        meta.setCountField(jsonObject.getString("countField"));
        meta.setRejectDuplicateRow(jsonObject.getBoolean("rejectDuplicateRow"));
        meta.setErrorDescription(jsonObject.getString("errorDescription"));
        meta.setCompareFields(jsonObject.getString("compareFields").split(","));

        String[] s = jsonObject.getString("caseInSensitive").split(",");
        boolean[] b = new boolean[s.length];
        for(int i=0;i<s.length;i++){
            b[i]=Boolean.valueOf(s[i]);
        }
        meta.setCaseInsensitive(b);

        StepMeta stepMeta = new StepMeta(jsonObject.getString("name"),meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
        transMeta.addStep(stepMeta);
    }
}
