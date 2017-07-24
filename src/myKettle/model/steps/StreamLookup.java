package myKettle.model.steps;

import org.json.JSONObject;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.streamlookup.StreamLookupMeta;

/**
 * Created by shengyichen on 17-7-5.
 * 流查询
 */
public class StreamLookup implements MyStepMeta {
    @Override
    public void setStepMeta(TransMeta transMeta, JSONObject jsonObject) {
        StreamLookupMeta meta = new StreamLookupMeta();
        meta.setDefault();
        meta.setKeystream(jsonObject.getString("keyStream").split(","));
        meta.setKeylookup(jsonObject.getString("keyLookup").split(","));
        meta.setValue(jsonObject.getString("value").split(","));
        meta.setValueName(jsonObject.getString("valueName").split(","));
        meta.setValueDefault(jsonObject.getString("valueDefault").split(","));

        String[] valueDefaultType = jsonObject.getString("valueDefaultType").split(",");
        int[] valueDefaultType_int = new int[valueDefaultType.length];
        for(int i = 0;i<valueDefaultType.length;i++){
            valueDefaultType_int[i] = Integer.valueOf(valueDefaultType[i]);
        }
        meta.setValueDefaultType(valueDefaultType_int);

        meta.setMemoryPreservationActive(jsonObject.getBoolean("memoryPreservationActive"));
        meta.setUsingIntegerPair(jsonObject.getBoolean("usingIntegerPair"));
        meta.setUsingSortedList(jsonObject.getBoolean("usingSortedList"));

        StepMeta stepMeta = new StepMeta(jsonObject.get("name").toString(),meta);
        stepMeta.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        stepMeta.drawStep();
        transMeta.addStep(stepMeta);
    }
}
