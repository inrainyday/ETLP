package myKettle.test;

import myKettle.utils.KettleUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransHopMeta;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.tableinput.TableInputMeta;
import org.pentaho.di.trans.steps.textfileoutput.TextFileOutputMeta;

/**
 * Created by lvyang on 5/9/17.
 */
public class KettleTaskJob2 {
    String a = "TableInput INPUTTABLE_t_lzfx_base_syonline kettle SELECT * FROM job ";



    TextFileOutputMeta tfo = new TextFileOutputMeta();

    public static void main(String[] args) throws Exception{
//        KettleTaskJob2 k = new KettleTaskJob2();
//        k.run();

//        KettleEnvironment.init();
        JSONObject jo = new JSONObject("{\"type\":\"TableInput\",\"name\":\"test_from\",\"db\":\"kettle\",\"sql\":\"SELECT * FROM job\",\"x\":\"200\",\"y\":\"200\"}");
        JSONObject jo1 = new JSONObject("{\"type\":\"TableInput\",\"name\":\"test_to\",\"db\":\"kettle\",\"sql\":\"SELECT * FROM job\",\"x\":\"200\",\"y\":\"400\"}");
        JSONObject jo2 = new JSONObject("{\"type\":\"TextFileOutput\",\"name\":\"txt_out\",\"filetype\":\"txt\",\"filename\":\"abc\",\"x\":\"400\",\"y\":\"300\"}");
        JSONObject jo3 = new JSONObject("{\"type\":\"TableOutput\",\"name\":\"tb_out\",\"db\":\"kettle\",\"target\":\"job\",\"x\":\"600\",\"y\":\"300\"}");

        JSONObject jhop = new JSONObject("{\"from\":\"test_from\",\"to\":\"test_to\"}");
        JSONArray jhops = new JSONArray("[{\"from\":\"test_from\",\"to\":\"test_to\"}]");
        String step = "["+jo.toString()+","+jo1.toString()+","+jo2.toString()+","+jo3.toString()+"]";
        String d = "{\"name\":\"kettle\",\"type\":\"MYSQL\",\"access\":\"Native\",\"host\":\"127.0.0.1\"," +
                "\"db\":\"etl\",\"port\":\"3306\",\"user\":\"root\",\"pass\":\"test\"}";
        String dd = "["+d+"]";
        String h = "[{\"from\":\"test_from\",\"to\":\"txt_out\"},{\"from\":\"test_to\",\"to\":\"txt_out\"},{\"from\":\"txt_out\",\"to\":\"tb_out\"}]";
//        System.out.println(dd);
//        System.out.println(step);
//        System.out.println(h);
        String t=KettleUtil.createTrans(dd,step,h);
        System.out.println(t);

//        TransMeta transMeta = new TransMeta();
//        transMeta.addDatabase(new DatabaseMeta("kettle", "MYSQL" , "Native", "127.0.0.1", "etl", "3306", "root", "test"));
    }
    public static String f(String s){
        return "\""+s+"\"";
    }
    public void run() throws Exception{
        KettleEnvironment.init();
        TransMeta transMeta = new TransMeta();
        DatabaseMeta dbm = new DatabaseMeta("kettle", "MYSQL" , "Native", "127.0.0.1", "etl", "3306", "root", "test");
        transMeta.addDatabase(dbm);
        String str = "[{\"zzm\":{\"name\":\"aaa\",\"type\":\"5\"}},{\"name\":\"bbbb\",\"type\":\"6\"},{\"name\":\"cccc\",\"type\":\"7\"}]";
        JSONArray ja = new JSONArray(str);
        JSONObject jo = new JSONObject("{\"type\":\"TableInput\",\"name\":\"INPUTTABLE_t_lzfx_base_syonline\",\"db\":\"kettle\",\"sql\":\"SELECT * FROM job\"}");
        JSONObject jj = new JSONObject(ja.get(0));
        TableInputMeta tableInputMeta = new TableInputMeta();
        tableInputMeta.setDatabaseMeta(dbm);
        tableInputMeta.setSQL(jo.getString("sql"));

        StepMeta tableInputStepMeta = new StepMeta(jo.get("name")+"",tableInputMeta);
        transMeta.addStep(tableInputStepMeta);
        tableInputStepMeta.setLocation(200, 200);
        tableInputStepMeta.drawStep();



        TextFileOutputMeta textFileOutputMeta = new TextFileOutputMeta();
        textFileOutputMeta.setDefault();
        textFileOutputMeta.setFilename("zzm");
        textFileOutputMeta.setExtension("txt");
        StepMeta textStepMeta = new StepMeta("go",textFileOutputMeta);
        transMeta.addStep(textStepMeta);
        textStepMeta.setLocation(400, 200);
        textStepMeta.drawStep();
        transMeta.addTransHop(new TransHopMeta(tableInputStepMeta, textStepMeta));
        TransHopMeta transHopMeta = new TransHopMeta();
        String transXml = transMeta.getXML().toString();
//        System.out.println(transXml);
        Trans trans = new Trans(transMeta);
//        trans.execute(null);
//        trans.waitUntilFinished();
//        System.out.println(trans.getResult());
    }

}
