package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.gettablenames.GetTableNamesMeta;

/**
 * Created by shengyichen on 17-7-3.
 * 获取表名
 */
public class GetTableNames implements MyStepMeta {
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        GetTableNamesMeta meta = new GetTableNamesMeta();
        meta.setDefault();
        DatabaseMeta databaseMeta = transMeta.findDatabase(jsonObject.getString("db"));
        meta.setDatabase(databaseMeta);
        meta.setSchemaName(jsonObject.getString("schemaName"));
        meta.setDynamicSchema(jsonObject.getBoolean("dynamicSchema"));

        meta.setIncludeCatalog(jsonObject.getBoolean("includeCatelog"));
        meta.setIncludeSchema(jsonObject.getBoolean("includeSchema"));
        meta.setIncludeTable(jsonObject.getBoolean("includeTable"));
        meta.setIncludeView(jsonObject.getBoolean("includeView"));
        meta.setIncludeProcedure(jsonObject.getBoolean("includeProcedureg"));
        meta.setIncludeSynonym(jsonObject.getBoolean("includeSynonym"));
        meta.setAddSchemaInOut(jsonObject.getBoolean("addSchemaInOut"));

        meta.setTablenameFieldName(jsonObject.getString("tableNameFieldName"));
        meta.setObjectTypeFieldName(jsonObject.getString("objectTypeFieldName"));
        meta.setIsSystemObjectFieldName(jsonObject.getString("isSystemObjectFieldName"));
        meta.setSQLCreationFieldName(jsonObject.getString("sqlCreateFieldName"));

        StepMeta stepMeta = new StepMeta(jsonObject.get("name").toString(),meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
        transMeta.addStep(stepMeta);
    }
}
