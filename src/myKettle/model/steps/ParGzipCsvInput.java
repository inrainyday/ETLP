package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.parallelgzipcsv.ParGzipCsvInputMeta;

/**
 * Created by zhangzhimin on 6/9/17.
 * Gzip CSV 文件输入
 * 压缩包的CSV文件输入
 */
public class ParGzipCsvInput implements MyStepMeta{
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        ParGzipCsvInputMeta meta = new ParGzipCsvInputMeta();
        meta.setDefault();
        //文件路径
        meta.setFilename(jsonObject.getString("filename"));
        //列分隔符
        meta.setDelimiter(jsonObject.getString("Delimiter"));
        //封闭符
        meta.setEnclosure(jsonObject.getString("enclosure"));
        //文件编码
        meta.setEncoding(jsonObject.getString("encoding"));
        StepMeta stepMeta = new StepMeta(jsonObject.getString("name"),meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
        transMeta.addStep(stepMeta);

    }
}
