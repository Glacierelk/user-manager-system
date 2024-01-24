package edu.hitwh.queryserviceprovider;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootTest
@EnableAspectJAutoProxy
@EnableDiscoveryClient
class QueryServiceProviderApplicationTests {

    @Test
    void contextLoads() {
    }

}
