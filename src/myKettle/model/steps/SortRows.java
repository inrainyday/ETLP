package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.steps.sort.SortRowsMeta;

/**
 * Created by zhangzhimin on 5/27/17.
 * SortRows(排序记录)
 */
public class SortRows implements MyStepMeta{
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        SortRowsMeta meta = new SortRowsMeta();
        meta.setDefault();
        meta.setDirectory(jsonObject.getString("directory"));
        meta.setPrefix(jsonObject.getString("preffix"));
        meta.setSortSize(jsonObject.getString("sortsize"));
        meta.setFreeMemoryLimit(jsonObject.getString("freememorylimit"));
        meta.setFieldName(jsonObject.getString("fieldname").split(","));
        String[] up = jsonObject.getString("ascending").split(",");
        int len = up.length;
        boolean[] ascending = new boolean[len];
        for(int i=0;i<len;i++){
            ascending[i] = Boolean.parseBoolean(up[i]);
        }
        meta.setAscending(ascending);
    }
}
