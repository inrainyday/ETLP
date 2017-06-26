package myKettle.view;

import org.springframework.http.HttpRequest;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

/**
 * Created by zhangzhimin on 6/21/17.
 */
public class MyFreeMarkerView extends FreeMarkerView {
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler){
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int port = request.getServerPort();
        String path = request.getContextPath();
        String basepath = scheme + "://" + serverName + ":" + port + path;
        request.setAttribute("basepath",basepath);
        return true;
    }
}
