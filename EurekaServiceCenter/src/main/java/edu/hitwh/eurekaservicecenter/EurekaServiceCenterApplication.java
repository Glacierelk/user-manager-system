package edu.hitwh.eurekaservicecenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServiceCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServiceCenterApplication.class, args);
    }

}
