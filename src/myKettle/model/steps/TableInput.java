package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.tableinput.TableInputMeta;

/**
 * Created by zhangzhimin on 5/10/17.
 * TableInput(表输入)
 */
public class TableInput implements MyStepMeta{
    @Override
    public void setStepMeta(TransMeta transMeta,JSONObject jsonObject) {
//        JSONObject jo = new JSONObject("{\"type\":\"TableInput\",\"name\":\"INPUTTABLE_t_lzfx_base_syonline\",\"db\":\"kettle\",\"sql\":\"SELECT * FROM job\"}");
        //新建元数据
        TableInputMeta meta = new TableInputMeta();
        //获取数据库连接
        DatabaseMeta databaseMeta = transMeta.findDatabase(jsonObject.getString("db"));
        //选择数据库连接
        meta.setDatabaseMeta(databaseMeta);
        //添加SQL
        meta.setSQL(jsonObject.getString("sql"));
        StepMeta tableInputStepMeta = new StepMeta(jsonObject.get("name")+"",meta);
        //设置图标坐标
        tableInputStepMeta.setLocation(jsonObject.getInt("x"), jsonObject.getInt("y"));
        //绘制元数据
        tableInputStepMeta.drawStep();
        transMeta.addStep(tableInputStepMeta);
    }

}
