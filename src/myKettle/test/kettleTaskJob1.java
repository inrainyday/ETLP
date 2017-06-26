package myKettle.test;

import myKettle.utils.KettleUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.Database;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.logging.StepLogTable;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.core.variables.Variables;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.tableinput.TableInputMeta;
import org.pentaho.di.trans.steps.tableoutput.TableOutputMeta;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Document;

import java.io.FileWriter;

/**
* Created by lvyang on 4/25/17.
*/
@Controller
public class kettleTaskJob1 {
    private static Logger logger = LogManager.getLogger(kettleTaskJob1.class.getName());

    //test
    /**
     * 两个库中的表名
     */
    public static String bjdt_tablename = "t_lzfx_base_syonline";
    public static String kettle_tablename = "syonline";
    public static String kettle_log = "t_lzfx_data_log";
    public static String base_tablename = "job";

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
                    "<database>etl</database>" +
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
    @RequestMapping("/HelloKettle1")
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
        DatabaseMeta database_bjdt = transMeta.findDatabase("kettle");
        oldTableInput.setDatabaseMeta(database_bjdt);
        String old_select_sql = "SELECT * FROM "+base_tablename;
        oldTableInput.setSQL(old_select_sql);

        //添加TableInputMeta到转换中
        StepMeta oldTableInputMetaStep = new StepMeta("INPUTTABLE_"+bjdt_tablename,oldTableInput);
        transMeta.addStep(oldTableInputMetaStep);
        oldTableInputMetaStep.setLocation(100, 100);
        oldTableInputMetaStep.drawStep();
        System.out.println("getTableFields  "+oldTableInput.getTableFields());
        System.out.println("getDatabaseMeta  "+oldTableInput.getDatabaseMeta());
        System.out.println("getDialogClassName  "+oldTableInput.getDialogClassName());


        //******************************************************************
        TableOutputMeta tom = new TableOutputMeta();
        tom.setDatabaseMeta(database_bjdt);
        StepMeta toms = new StepMeta("output"+bjdt_tablename,tom);
        transMeta.addStep(toms);
        toms.setLocation(200,200);
        toms.drawStep();
        String transXml = transMeta.getXML().toString().trim();
//        System.out.println("transXml:"+transXml);
//        String sql = "insert into job (body) values('"+transXml+"')";
        String sql = "select * from job where job_id =3 ";
//        System.out.println(sql);
        Trans trans = new Trans(transMeta);
        Database db = new Database(database_bjdt);
//        db.connect();
//        System.out.println("db***********************************"+db.execStatement(sql).toString());
//        trans.execute(null); // You can pass arguments instead of null.
        trans.waitUntilFinished();
        if ( trans.getErrors() > 0 )
        {
            throw new RuntimeException( "There were errors during transformation execution." );
        }
       System.out.println("***********the end************");
       return transXml;
    }

    public static void main(String[] args) throws Exception{
        kettleTaskJob1 job = new kettleTaskJob1();
        //job.run();
        String l = job.DBrun();
        System.out.println(l);
//        TransMeta transMeta = new TransMeta(l);
//        Document doc = XMLHandler.loadXMLString(l);
//        System.out.println(doc);
        String path = job.getClass().getResource("/myKettle").getPath();
        System.out.println(path);

        FileWriter fileWriter = null;
        fileWriter = new FileWriter(path+"/fw.ktr");
        fileWriter.write(l);
        fileWriter.close();
        KettleUtil.callNativeTrans(path+"/fw.ktr");
    }
}
