package xyz.ivyxjc.coderoad.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "server");
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
