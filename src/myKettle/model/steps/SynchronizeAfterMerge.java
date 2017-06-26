package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.synchronizeaftermerge.SynchronizeAfterMergeMeta;

/**
 * Created by zhangzhimin on 5/17/17.
 * SynchronizeAfterMerge(数据同步)
 */
public class SynchronizeAfterMerge implements MyStepMeta{
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        SynchronizeAfterMergeMeta meta = new SynchronizeAfterMergeMeta();
        //初始化
        meta.setDefault();
        //设置数据库连接
        meta.setDatabaseMeta(transMeta.findDatabase(jsonObject.getString("db")));
        //设置目标表名
        meta.setTableName(jsonObject.getString("table"));
        //设置提交数量
        meta.setCommitSize(jsonObject.getInt("size"));
        //设置用来查询的字段
        meta.setKeyLookup(jsonObject.getString("key").split("'"));
        //设置流里的字段
        meta.setKeyStream(jsonObject.getString("streamkey").split(","));
        meta.setKeyStream2(new String[]{""});
        //设置比较符
        meta.setKeyCondition(new String[]{"="});
        meta.setKeyCondition(jsonObject.getString("keycondition").split(","));
        //更新的表字段
        meta.setUpdateLookup(jsonObject.getString("updatelookup").split(","));
        //与表字段对应的流字段
        meta.setUpdateStream(jsonObject.getString("updatestream").split(","));
        //
        String[] up = jsonObject.getString("update").split(",");
        int len = up.length;
        Boolean[] update = new Boolean[len];
        for(int i=0;i<len;i++){
            update[i] = Boolean.parseBoolean(up[i]);
        }
        //设置每个字段是否进行更新
        meta.setUpdate(update);


        //设置高级属性(操作)
        //设置操作字段名
        meta.setOperationOrderField(jsonObject.getString("operation"));
        //当值相等时插入
        meta.setOrderDelete(jsonObject.getString("orderdelete"));
        //当值相等时插入
        meta.setOrderInsert(jsonObject.getString("orderinsert"));
        //当值相等时更新
        meta.setOrderUpdate(jsonObject.getString("orderupdate"));
        StepMeta stepMeta = new StepMeta(jsonObject.getString("name"),meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
    }

}
