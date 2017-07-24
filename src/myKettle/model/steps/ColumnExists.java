package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.columnexists.ColumnExistsMeta;

/**
 * Created by shengyichen on 17-7-4.
 * 检查表里的列是否存在
 */
public class ColumnExists implements MyStepMeta {

    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        ColumnExistsMeta meta = new ColumnExistsMeta();
        meta.setDefault();
        DatabaseMeta databaseMeta = transMeta.findDatabase(jsonObject.getString("db"));
        meta.setDatabase(databaseMeta);
        meta.setSchemaname(jsonObject.getString("schemaName"));
        meta.setTablename(jsonObject.getString("tableName"));
        meta.setTablenameInField(jsonObject.getBoolean("tableNameInField"));
        meta.setDynamicTablenameField(jsonObject.getString("tableNameField"));
        meta.setDynamicColumnnameField(jsonObject.getString("columnNameField"));
        meta.setResultFieldName(jsonObject.getString("resultFieldName"));

        StepMeta stepMeta = new StepMeta(jsonObject.get("name").toString(),meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
        transMeta.addStep(stepMeta);
    }
}
