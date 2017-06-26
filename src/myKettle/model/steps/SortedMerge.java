package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.steps.sortedmerge.SortedMergeMeta;

/**
 * Created by zhangzhimin on 5/27/17.
 * SorterMerge(排序合并)
 */
public class SortedMerge implements MyStepMeta{
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        SortedMergeMeta meta = new SortedMergeMeta();
        meta.setDefault();
        meta.setFieldName(jsonObject.getString("filename").split(","));
        String[] up = jsonObject.getString("ascending").split(",");
        int len = up.length;
        boolean[] ascending = new boolean[len];
        for(int i=0;i<len;i++){
            ascending[i] = Boolean.parseBoolean(up[i]);
        }
        meta.setAscending(ascending);
    }
}
