package myKettle.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.Result;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.KettleLogLayout;
import org.pentaho.di.core.logging.KettleLogStore;
import org.pentaho.di.core.logging.StepLogTable;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.core.variables.Variables;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.job.entry.JobEntryCopy;
import org.pentaho.di.job.entry.JobEntryInterface;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransHopMeta;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.infobrightoutput.KettleEtlLogger;
import org.pentaho.di.www.Carte;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


public class KettleUtil{
    //执行无参转换
    public  static  String callNativeTrans(String transFileName) throws Exception{
        return callNativeTransWithParams(null,transFileName);
    }
    //执行带参数的转换
    public  static  String callNativeTransWithParams(String[] params,String transFileName) throws Exception{
        KettleEnvironment.init();
        EnvUtil.environmentInit();
        //项目目录下的文件名 *.ktr
        TransMeta transMeta = new TransMeta(transFileName);
       // Trans trans = new Trans(transMeta);
        Trans trans = null;
//        trans.execute(params);
//        trans.waitUntilFinished();

        //定义变量
        VariableSpace space = new Variables();
        //将step日志数据库配置名加入到变量集中
        space.setVariable("t_lzfx_data_log","t_lzfx_data_log");
        space.initializeVariablesFrom(null);


        try {
            DatabaseMeta databaseMeta = new DatabaseMeta( "<connection>" +
                    "<name>t_lzfx_data_log</name>" +
                    "<server>127.0.0.1</server>" +
                    "<type>MYSQL</type>" +
                    "<access>Native</access>" +
                    "<database>etl</database>" +
                    "<port>3306</port>" +
                    "<username>root</username>" +
                    "<password>sorry</password>" +
                    "</connection>");
            transMeta.addDatabase(databaseMeta);

            //声明StepLogTable。space-系统变量，tanMetade1-提供数据库连接
            StepLogTable stepLogTable = StepLogTable.getDefault(space,transMeta);

            //StepLogTable使用的数据库连接名（上面配置的变量名）。
            stepLogTable.setConnectionName("t_lzfx_data_log");
            //设置Step日志的表名
            stepLogTable.setTableName("t_lzfx_data_log");
            //设置TransMeta的StepLogTable
            transMeta.setStepLogTable(stepLogTable);
            // 转换
            trans = new Trans(transMeta);
            // 执行转换
            trans.execute(null);
            // 等待转换执行结束
            trans.waitUntilFinished();
            //抛出异常
            if(trans.getErrors()>0){
                throw new Exception("There are errors during transformation exception!(传输过程中发生异常)");
            }

        } catch (Exception e) {
            if(trans!=null){
                trans.stopAll();
            }
            e.printStackTrace();
            throw new KettleException(e);
        }

        System.out.println("------------------------------------");
        System.out.println(KettleLogStore.getAppender().getBuffer().toString());
        KettleLogStore.getAppender().clear();
        System.out.println("------------------------------------");
      //  System.out.println(transMeta.toString());



        if(trans.getErrors()>0){
            throw new Exception("转换异常");
        }
        return transMeta.toString();
    }
    /**
     * 执行作业
     * @param jobName  作业路径及名称
     */
    public static void callNativeJob(String jobName) throws Exception{
        JobMeta jobMeta = new JobMeta(jobName,null);
        Job job = new Job(null,jobMeta);
//      向job传递参数
//      job.setVariable(paramname,paramvalue);
        job.start();
        job.waitUntilFinished();
        if(job.getErrors()>0){
            throw new Exception("作业异常");
        }
    }
    /**
     * 根据参数为作业添加作业项信息
     * @param jobMeta  元数据
     * @param jsonObject       步骤关系
     */
    public static String addEntry(JobMeta jobMeta,JSONObject jsonObject) throws Exception{
        Class c = Class.forName("myKettle.model.jobs." + jsonObject.get("type"));
        Constructor constructor = c.getConstructor();
        Object ob = constructor.newInstance();
        Method method = c.getMethod("setEntry",JobMeta.class,JSONObject.class);
        method.invoke(ob, jobMeta,jsonObject);
        return jobMeta.getXML();
    }
    /**
     * 根据参数为转换添加步骤信息
     * @param transMeta  元数据
     * @param jsonObject       步骤关系
     */
    public static String addStep(TransMeta transMeta,JSONObject jsonObject) throws Exception{
        Class c = Class.forName("myKettle.model.steps." + jsonObject.get("type"));
        Constructor constructor = c.getConstructor();
        Object ob = constructor.newInstance();
        Method method = c.getMethod("setStepMeta",TransMeta.class,JSONObject.class);
        method.invoke(ob, transMeta,jsonObject);
        return transMeta.getXML();
    }
    /**
     * 设置步骤间关系
     * @param transMeta  元数据
     * @param hops       步骤关系
     */
    public static void addTransHop(TransMeta transMeta,JSONObject hops) throws Exception{
        //获取现有step
        StepMeta[] steps = transMeta.getStepsArray();
        StepMeta from = null;StepMeta to = null;
        for (int i=0;i<steps.length;i++){
            //获取起始步骤
            if(hops.get("from").equals(steps[i].toString())){
                from = steps[i];
                System.out.println("from "+steps[i]);
            }
            //获取目标步骤
            if(hops.get("to").equals(steps[i].toString())){
                to = steps[i];
                System.out.println("to "+steps[i]);
            }
        }
        TransHopMeta transHopMeta = new TransHopMeta();
        if(from!=null&&to!=null){
            transMeta.addTransHop(new TransHopMeta(from,to));
        }
    }
    /**
     * 创建转换元数据
     * @param dbs        数据库信息
     * @param steps      步骤信息
     * @param hops       步骤关系
     */
    public static String createTrans(String dbs,String steps,String hops) throws Exception{
        //初始化Kettle
        KettleEnvironment.init();
        TransMeta transMeta = new TransMeta();
        //数据库信息
        JSONArray jdbs = new JSONArray(dbs);
        //步骤元数据
        JSONArray jsteps = new JSONArray(steps);
        //步骤间的关系
        JSONArray jhops = new JSONArray(hops);
        JSONObject jdb = null;
        JSONObject jstep = null;
        JSONObject jhop = null;
        //添加数据库连接
        for(int i=0;i<jdbs.length();i++){
            jdb = jdbs.getJSONObject(i);
            //String name, String type, String access, String host, String db, String port, String user, String pass)
            transMeta.addDatabase(new DatabaseMeta(jdb.getString("name"),jdb.getString("type"),jdb.getString("access")
                    ,jdb.getString("host"),jdb.getString("db"),jdb.getString("port"),jdb.getString("user"),jdb.getString("pass")));
        }
        //设置日志表
//        StepLogTable stepLogTable = StepLogTable.getDefault(null,transMeta);
//        //参数待添加
//        stepLogTable.setConnectionName("");
//        stepLogTable.setTableName("");
//        transMeta.setStepLogTable(stepLogTable);
        //添加步骤
        for(int i =0;i<jsteps.length();i++){
            jstep = jsteps.getJSONObject(i);
            KettleUtil.addStep(transMeta, jstep);
        }
        //设置步骤间的关系
        for (int i = 0;i<jhops.length();i++){
            jhop = jhops.getJSONObject(i);
            KettleUtil.addTransHop(transMeta,jhop);
        }
        //转换元数据
        String ktr = transMeta.getXML();

        return ktr;
    }
    //创建作业元数据
    public static String createJob() throws Exception{
        KettleEnvironment.init();
        return "";
    }
    public static String[] format(String s){
        return s.split(",");
    }
    public static void drawEntry(JobEntryInterface entry,JobEntryCopy jobEntryCopy,JSONObject jsonObject){
        jobEntryCopy.setEntry(entry);
        jobEntryCopy.setLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
        jobEntryCopy.setDrawn(true);
    }
}