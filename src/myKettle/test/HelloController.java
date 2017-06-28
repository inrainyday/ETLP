package myKettle.test;

import myKettle.utils.KettleUtil;
import org.json.JSONArray;
import org.pentaho.di.www.Carte;
import org.pentaho.di.www.CarteServlet;
import org.pentaho.di.www.SlaveServerConfig;
import org.pentaho.di.www.TransformationMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by lvyang on 5/3/17.
 */
@Controller
@RequestMapping("/hi")
//@RestController
public class HelloController {
    @Value("${userName}")
    private String userName;
    @Value("${bookTitle}")
    private String bookTitle;
    @RequestMapping("/hello")
    public String hello(ModelMap modelMap){
        String src = getClass().getResource("/").getPath();
        String src1 = getClass().getResource("").getFile();
        modelMap.addAttribute("message",userName);
        modelMap.addAttribute("name",src);
        modelMap.addAttribute("bookTitle",src1);
        System.out.println("WTF***************************");
        return "welcome";
    }
    @RequestMapping("/abc")
    public String showTrans(){

        return "showTrans";
    }
    @Autowired
    private MyConfig myConfig;
    @RequestMapping("/config")
    public Object getConfig(){
        return myConfig.getServers();
    }
    public void CreateTransXML(String params){
        JSONArray ja = new JSONArray(params);        
    }
    
    public void testCarte() throws Exception{
        SlaveServerConfig slaveServerConfig = new SlaveServerConfig();
        Carte c = new Carte(slaveServerConfig);
        c.getWebServer().getTransformationMap();
        TransformationMap transformationMap;
    }
    
}
