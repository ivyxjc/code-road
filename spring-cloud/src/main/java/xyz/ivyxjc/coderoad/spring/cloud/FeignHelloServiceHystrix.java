package xyz.ivyxjc.coderoad.spring.cloud;

import org.springframework.stereotype.Service;

@Service
public class FeignHelloServiceHystrix implements FeignHelloService {
    @Override
    public String sayHiFromClientOne(String name) {
        return "sorry, " + name;
    }
}
