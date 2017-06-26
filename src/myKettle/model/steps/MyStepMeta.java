package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;

/**
 * Created by lvyang on 5/10/17.
 */
public interface MyStepMeta {
    //设置元数据属性
    public void setStepMeta(TransMeta transMeta,JSONObject jsonObject);
}
