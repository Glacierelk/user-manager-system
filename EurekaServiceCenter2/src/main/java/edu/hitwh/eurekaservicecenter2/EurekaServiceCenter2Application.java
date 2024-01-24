package edu.hitwh.eurekaservicecenter2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServiceCenter2Application {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServiceCenter2Application.class, args);
    }

}
