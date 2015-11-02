package org.tsharding.main;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@EnableAutoConfiguration
@ComponentScan("org.tsharing")
@Configuration
public class BeeBootstrap {

    public static void main(String[] args) {
        new BeeBootstrap().start(args);
    }

    private void start(String[] args){
        SpringApplication.run(BeeBootstrap.class, args);
    }

}
