package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.steps.denormaliser.DenormaliserMeta;

/**
 * Created by zhangzhimin on 6/7/17.
 * Denormaliser(列转行)
 */
public class Denormaliser implements MyStepMeta{
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        DenormaliserMeta meta = new DenormaliserMeta();
    }
}
