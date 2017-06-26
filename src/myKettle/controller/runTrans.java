package myKettle.controller;

import myKettle.utils.KettleUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lvyang on 5/3/17.
 */
@Controller
public class runTrans {
    @RequestMapping("/runTrans")
    public String run(String transFileName,ModelMap modelMap) throws Exception{
        String metaData = KettleUtil.callNativeTrans(transFileName);
        modelMap.addAttribute("metaData",metaData);
        return "runTrans";
    }
}
