package xyz.ivyxjc.coderoad.spring.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Profile("feign")
@RestController
public class FeignHelloController {

    @Autowired
    private FeignHelloService feignHelloService;

    @GetMapping("/hi")
    public String hello(@RequestParam String name) {
        return feignHelloService.sayHiFromClientOne(name);
    }
}
