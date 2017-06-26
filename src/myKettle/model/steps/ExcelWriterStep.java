package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.excelwriter.ExcelWriterStepMeta;

/**
 * Created by zhangzhimin on 6/7/17.
 * ExcelWriteStep(MicroSoft Excel输出)
 * 相比Excel输出支持更新的Excel版本
 */
public class ExcelWriterStep implements MyStepMeta{
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        ExcelWriterStepMeta meta = new ExcelWriterStepMeta();
        meta.setDefault();
        meta.setFileName(jsonObject.getString("filename"));
        meta.setExtension(jsonObject.getString("extension"));
        meta.setSheetname(jsonObject.getString("sheetname"));
        //文件名中包含日期
        meta.setDateInFilename(jsonObject.getBoolean("dateinfilename"));
        //文件名中包含时间
        meta.setTimeInFilename(jsonObject.getBoolean("timeinfilename"));
        //如果文件已存在 new reuse
        meta.setIfFileExists(jsonObject.getString("iffileexists"));
        //设为活动工作表
        meta.setAutoSizeColums(jsonObject.getBoolean("autosizecolumns"));
        //如果输出文件中已存在工作表 new reuse
        meta.setIfSheetExists(jsonObject.getString("ifsheetexists"));

        StepMeta stepMeta = new StepMeta(jsonObject.get("name").toString(),meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
        transMeta.addStep(stepMeta);
    }
}
