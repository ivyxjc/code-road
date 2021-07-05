package xyz.ivyxjc.coderoad.spring.cloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class ServiceClientApplication {

    @Value("${server.port}")
    private String port;

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "client");
        SpringApplication.run(ServiceClientApplication.class, args);
    }

    @RequestMapping("/hi")
    public String home(@RequestParam(value = "name", defaultValue = "forezp") String name) {
        return String.format("hi %s, i am from port %s", name, port);
    }
}
