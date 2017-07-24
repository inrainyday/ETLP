package myKettle.controller;

import myKettle.model.Trans;
import myKettle.utils.DBUtils;
import myKettle.utils.KettleUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.util.List;

@Controller
@RequestMapping("/kettle")
public class KettleController {

    @RequestMapping(value = "insertTrans", method = {RequestMethod.POST, RequestMethod.GET})
    public void insertTrans(Trans trans, HttpServletRequest request, HttpServletResponse response) throws Exception{
        trans.setMetadata(KettleUtil.createTrans(trans.getDbs(),trans.getSteps(),trans.getHops()));
        Object[] args = new Object[7];
        Connection conn = null;
        DBUtils dbUtils = new DBUtils();
        try {
            conn = dbUtils.getConnection();
            String sql = "insert into trans (metadata,metadata_desc,cron_expression,cron_desc,trans_name,schedule_stat,owner_id) values (?,?,?,?,?,?,?)";
            args[0] = trans.getMetadata();
            args[1] = trans.getMetadata_desc();
            args[2] = trans.getCron_expression();
            args[3] = trans.getCron_desc();
            args[4] = trans.getTrans_name();
            args[5] = trans.getSchedule_stat();
            args[6] = trans.getOwner_id();
            int result = dbUtils.insertOne(conn,sql,args);
            response.getWriter().print(result);
        }catch (Exception e){
            response.getWriter().write("false");
        }finally {
            conn.close();;
        }
    }

    @RequestMapping(value = "deleteTransById", method = {RequestMethod.POST, RequestMethod.GET})
    public void deleteTransById(int trans_id, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{

        Connection conn = null;
        DBUtils dbUtils = new DBUtils();
        try {
            conn = dbUtils.getConnection();
            String sql = "delete from dbs where trans_id = "+trans_id;
            int result = dbUtils.deleteOne(conn,sql);
            response.getWriter().print(result);
        }catch (Exception e){
            response.getWriter().write("false");
        }finally {
            conn.close();;
        }
    }
}
