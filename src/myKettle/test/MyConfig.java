package myKettle.test;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by zhangzhimin on 6/21/17.
 */
@Component
@ConfigurationProperties(prefix = "my")
public class MyConfig {
    private List<String> servers;

    public List<String> getServers() {
        return servers;
    }

    public void setServers(List<String> servers) {
        this.servers = servers;
    }
}
