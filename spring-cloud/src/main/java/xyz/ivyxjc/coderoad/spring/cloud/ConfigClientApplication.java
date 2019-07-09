package xyz.ivyxjc.coderoad.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ConfigClientApplication {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "config-client");
        SpringApplication.run(ConfigClientApplication.class, args);
    }


}
