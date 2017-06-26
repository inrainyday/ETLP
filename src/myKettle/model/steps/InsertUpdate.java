package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.steps.insertupdate.InsertUpdateMeta;

/**
 * Created by zhangzhimin on 5/25/17.
 */
public class InsertUpdate implements MyStepMeta{
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        InsertUpdateMeta meta = new InsertUpdateMeta();
        meta.setDefault();
        DatabaseMeta databaseMeta = transMeta.findDatabase(jsonObject.getString("db"));
        meta.setDatabaseMeta(databaseMeta);
        meta.setTableName(jsonObject.getString("tablename"));
        meta.setCommitSize(jsonObject.getInt("commitsize"));
        meta.setKeyStream(jsonObject.getString("keystream").split(","));
        meta.setKeyStream2(null);
        meta.setUpdateStream(jsonObject.getString("updatestream").split(","));
        String[] up = jsonObject.getString("update").split(",");
        int len = up.length;
        Boolean[] update = new Boolean[len];
        for(int i=0;i<len;i++){
            update[i] = Boolean.parseBoolean(up[i]);
        }
        //设置每个字段是否进行更新
        meta.setUpdate(update);
        meta.setKeyLookup(jsonObject.getString("keylookup").split(","));

    }
}
