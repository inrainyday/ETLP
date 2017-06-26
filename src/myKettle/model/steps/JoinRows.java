package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.steps.joinrows.JoinRowsMeta;

/**
 * Created by zhangzhimin on 5/27/17.
 * JoinRows(记录关联-笛卡尔输出)
 */
public class JoinRows implements MyStepMeta{
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        JoinRowsMeta meta = new JoinRowsMeta();
        meta.setDefault();
        meta.setDirectory(jsonObject.getString("directory"));
        meta.setPrefix(jsonObject.getString("preffix"));
        meta.setCacheSize(jsonObject.getInt("cachesize"));
        meta.setMainStep(transMeta.findStep(jsonObject.getString("mainstep")));

        //条件待添加
    }
}
