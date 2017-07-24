package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.filestoresult.FilesToResultMeta;

/**
 * Created by shengyichen on 17-7-5.
 * 复制文件到结果
 */
public class FilesToResult implements MyStepMeta {
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        FilesToResultMeta meta = new FilesToResultMeta();
        meta.setDefault();
        meta.setFilenameField(jsonObject.getString("fileName"));
        meta.setFileType(jsonObject.getInt("fileType"));

        StepMeta stepMeta = new StepMeta(jsonObject.get("name").toString(),meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
        transMeta.addStep(stepMeta);
    }
}
