package myKettle.model.steps;

import org.json.JSONArray;
import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.textfileoutput.TextFileOutputMeta;

/**
 * Created by zhangzhimin on 5/11/17.
 * TextFileOutput(文件输出)
 */
public class TextFileOutput implements MyStepMeta{
    @Override
    public void setStepMeta(TransMeta transMeta,JSONObject jsonObject){
        TextFileOutputMeta meta = new TextFileOutputMeta();
        meta.setDefault();
        meta.setFilename(jsonObject.get("filename").toString());
        meta.setExtension(jsonObject.get("filetype").toString());
        StepMeta stepMeta = new StepMeta(jsonObject.get("name").toString(),meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
        transMeta.addStep(stepMeta);
    }
}
