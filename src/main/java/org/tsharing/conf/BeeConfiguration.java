package org.tsharing.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author zhaoshb
 * @Since bee-im1.0
 */
@Configuration
@PropertySource("classpath:bee.properties")
public class BeeConfiguration {

    @Value("${server.listen.port}")
    private Integer serverListenPort = null;

    public Integer getServerListenPort() {
        return serverListenPort;
    }
}
