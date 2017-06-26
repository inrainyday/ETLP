package myKettle.test;

import myKettle.utils.KettleUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.Database;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.logging.StepLogTable;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.core.variables.Variables;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransHopMeta;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepIOMetaInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.mergerows.MergeRowsMeta;
import org.pentaho.di.trans.steps.synchronizeaftermerge.SynchronizeAfterMergeMeta;
import org.pentaho.di.trans.steps.tableinput.TableInputMeta;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* Created by lvyang on 4/25/17.
*/
@Controller
public class kettleTaskJob{
    private static Logger logger = LogManager.getLogger(kettleTaskJob.class.getName());

    //test
    /**
     * 两个库中的表名
     */
    public static String bjdt_tablename = "t_lzfx_base_syonline";
    public static String kettle_tablename = "syonline";
    public static String kettle_log = "t_lzfx_data_log";

    /**
     * 数据库连接信息,适用于DatabaseMeta其中 一个构造器DatabaseMeta(String xml)
     */
    public static final String[] databasesXML = {
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<connection>" +
                    "<name>bjdt</name>" +
                    "<server>127.0.0.1</server>" +
                    "<type>MYSQL</type>" +
                    "<access>Native</access>" +
                    "<database>etl</database>" +
                    "<port>3306</port>" +
                    "<username>root</username>" +
                    "<password>test</password>" +
                    "</connection>",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<connection>" +
                    "<name>kettle</name>" +
                    "<server>127.0.0.1</server>" +
                    "<type>MYSQL</type>" +
                    "<access>Native</access>" +
                    "<database>kettledb</database>" +
                    "<port>3306</port>" +
                    "<username>root</username>" +
                    "<password>test</password>" +
                    "</connection>"
    };



    public void run() throws Exception{
//        logger.warn("kettling");
//        String transFileName = "Generate Row - basics.ktr";
//        KettleUtil.callNativeTrans(transFileName);
//        logger.warn("kettle end");

    }
    @RequestMapping("/HelloKettle")
    public String DBrun() throws Exception{
        KettleEnvironment.init();
        TransMeta transMeta = new TransMeta();
        for(int i=0;i<databasesXML.length;i++){
            DatabaseMeta dbm = new DatabaseMeta(databasesXML[i]);
            transMeta.addDatabase(dbm);
        }
        VariableSpace space = new Variables();
        //将step日志数据库配置名加入到变量集中
        space.setVariable("kettle_log","bjdt");
        space.initializeVariablesFrom(null);
        StepLogTable stepLogTable = StepLogTable.getDefault(space,transMeta);
        stepLogTable.setConnectionName("bjdt");
        //设置Step日志的表名
        stepLogTable.setTableName(kettle_log);
        //设置TransMeta的StepLogTable
        transMeta.setStepLogTable(stepLogTable);
        //******************************************************************
        //第一个表输入步骤(原表数据输入)
        TableInputMeta oldTableInput = new TableInputMeta();
        DatabaseMeta database_bjdt = transMeta.findDatabase("bjdt");
        oldTableInput.setDatabaseMeta(database_bjdt);
        String old_select_sql = "SELECT ID,IP,CREATEDATETIME,LOGINNAME,TYPE FROM "+bjdt_tablename;
        oldTableInput.setSQL(old_select_sql);

        //添加TableInputMeta到转换中
        StepMeta oldTableInputMetaStep = new StepMeta("INPUTTABLE_"+bjdt_tablename,oldTableInput);
        transMeta.addStep(oldTableInputMetaStep);
        oldTableInputMetaStep.setLocation(100,100);
        oldTableInputMetaStep.drawStep();
        //*****************************************************************
        //第二个表输入步骤(原表数据输入)
        TableInputMeta newTableInput = new TableInputMeta();
        //给表输入添加一个DatabaseMeta连接数据库
        DatabaseMeta database_kettle = transMeta.findDatabase("bjdt");
        newTableInput.setDatabaseMeta(database_kettle);
        String new_select_sql = "SELECT ID,IP,CREATEDATETIME,LOGINNAME,TYPE FROM "+kettle_tablename;
        newTableInput.setSQL(new_select_sql);
        //添加TableInputMeta到转换中
        StepMeta newTableInputMetaStep = new StepMeta("INPUTTABLE_"+kettle_tablename,newTableInput);
        //给步骤添加在spoon工具中的显示位置
        transMeta.addStep(newTableInputMetaStep);
        newTableInputMetaStep.setLocation(100,400);
        newTableInputMetaStep.drawStep();

        //******************************************************************
        //第三个步骤合并
        MergeRowsMeta mergeRowsMeta = new MergeRowsMeta();
        //设置合并步骤的新旧数据源
        StepIOMetaInterface stepIOMeta = mergeRowsMeta.getStepIOMeta();
        stepIOMeta.getInfoStreams().get(0).setStepMeta(newTableInputMetaStep);
        stepIOMeta.getInfoStreams().get(1).setStepMeta(oldTableInputMetaStep);
        mergeRowsMeta.setFlagField("bz"); //设置标志字段
        mergeRowsMeta.setKeyFields(new String[]{"ID"});
        mergeRowsMeta.setValueFields(new String[]{"IP","CREATEDATETIME","LOGINNAME","TYPE"});
        StepMeta mergeStepMeta = new StepMeta("合并记录", mergeRowsMeta);
        transMeta.addStep(mergeStepMeta);
        mergeStepMeta.setLocation(300,300);
        mergeStepMeta.drawStep();
        //******************************************************************
        //添加HOP把两个输入和合并的步骤关联
        transMeta.addTransHop(new TransHopMeta(oldTableInputMetaStep, mergeStepMeta));
        transMeta.addTransHop(new TransHopMeta(newTableInputMetaStep, mergeStepMeta));
        //******************************************************************

        //******************************************************************
        //第四个步骤同步数据
        SynchronizeAfterMergeMeta synchronizeAfterMergeMeta = new SynchronizeAfterMergeMeta();
        synchronizeAfterMergeMeta.setCommitSize(10000); //设置事务提交数量
        synchronizeAfterMergeMeta.setDatabaseMeta(database_kettle); //目标数据源
        synchronizeAfterMergeMeta.setSchemaName("");//数据表schema
        synchronizeAfterMergeMeta.setTableName(kettle_tablename); //数据表名称
        synchronizeAfterMergeMeta.setUseBatchUpdate(true); //设置批量更新
        //设置用来查询的关键字
        synchronizeAfterMergeMeta.setKeyLookup(new String[]{"ID"}); //设置用来查询的关键字
        synchronizeAfterMergeMeta.setKeyStream(new String[]{"ID"}); //设置流输入的字段
        synchronizeAfterMergeMeta.setKeyStream2(new String[]{""});//一定要加上
        synchronizeAfterMergeMeta.setKeyCondition(new String[]{"="}); //设置操作符

        //设置要更新的字段
        String[] updatelookup = {"ID","IP","CREATEDATETIME","LOGINNAME","TYPE"} ;
        String [] updateStream = {"ID","IP","CREATEDATETIME","LOGINNAME","TYPE"};
        Boolean[] updateOrNot = {false,true,true,true,true};
        synchronizeAfterMergeMeta.setUpdateLookup(updatelookup);
        synchronizeAfterMergeMeta.setUpdateStream(updateStream);
        synchronizeAfterMergeMeta.setUpdate(updateOrNot);

        //设置高级属性(操作)
        synchronizeAfterMergeMeta.setOperationOrderField("bz"); //设置操作标志字段名
        synchronizeAfterMergeMeta.setOrderInsert("new");
        synchronizeAfterMergeMeta.setOrderUpdate("changed");
        synchronizeAfterMergeMeta.setOrderDelete("deleted");
        StepMeta synStepMeta = new StepMeta("数据同步", synchronizeAfterMergeMeta);
        transMeta.addStep(synStepMeta);
        synStepMeta.setLocation(400,300);
        synStepMeta.drawStep();

        //添加HOP把合并和数据同步的步骤关联
        transMeta.addTransHop(new TransHopMeta(mergeStepMeta,synStepMeta));
        //******************************************************************

        String transXml = transMeta.getXML().toString().trim();
        System.out.println("transXml:"+transXml);
        String test = "<just test>";
        String sql = "insert into job (body) values('"+transXml+"')";
        System.out.println(sql);
        Trans trans = new Trans(transMeta);
        Database db = new Database(database_bjdt);
        db.connect();
        db.execStatement(sql);
        trans.execute(null); // You can pass arguments instead of null.
        trans.waitUntilFinished();
        if ( trans.getErrors() > 0 )
        {
            throw new RuntimeException( "There were errors during transformation execution." );
        }
       System.out.println("***********the end************");
       return transXml;
    }

    public static void main(String[] args) throws Exception{
//        kettleTaskJob job = new kettleTaskJob();
        //job.run();
//        job.DBrun();
        KettleEnvironment.init();
        JSONObject jo = new JSONObject("{\"type\":\"TableInput\",\"name\":\"test_from\",\"db\":\"kettle\",\"sql\":\"SELECT * FROM job\",\"x\":\"200\",\"y\":\"200\"}");
        JSONObject jo1 = new JSONObject("{\"type\":\"TableInput\",\"name\":\"test_to\",\"db\":\"kettle\",\"sql\":\"SELECT * FROM job\",\"x\":\"200\",\"y\":\"400\"}");
        JSONObject jo2 = new JSONObject("{\"type\":\"TextFileOutput\",\"name\":\"txt_out\",\"filetype\":\"txt\",\"filename\":\"abc\",\"x\":\"400\",\"y\":\"300\"}");
        JSONObject jo3 = new JSONObject("{\"type\":\"TableOutput\",\"name\":\"tb_out\",\"db\":\"kettle\",\"target\":\"job\",\"x\":\"600\",\"y\":\"300\"}");
        JSONObject jo4 = new JSONObject("{\"type\":\"MergeRows\",\"name\":\"test_merge\",\"old\":\"test_from\",\"new\":\"test_to\",\"flag\":\"bz\",\"key\":\"a\",\"value\":\"b,c,d\",\"x\":\"400\",\"y\":\"400\"}");

        JSONObject jhop = new JSONObject("{\"from\":\"test_from\",\"to\":\"test_to\"}");
        JSONArray jhops = new JSONArray("[{\"from\":\"test_from\",\"to\":\"test_to\"}]");
        String step = "["+jo.toString()+","+jo1.toString()+","+jo2.toString()+","+jo4.toString()+"]";
        String d = "{\"name\":\"kettle\",\"type\":\"MYSQL\",\"access\":\"Native\",\"host\":\"127.0.0.1\"," +
                "\"db\":\"etl\",\"port\":\"3306\",\"user\":\"root\",\"pass\":\"test\"}";
        String dd = "["+d+"]";
        String h = "[{\"from\":\"test_from\",\"to\":\"test_merge\"},{\"from\":\"test_to\",\"to\":\"test_merge\"}]";
        String x = KettleUtil.createTrans(dd,step,h);
        System.out.println(x);

    }
}
