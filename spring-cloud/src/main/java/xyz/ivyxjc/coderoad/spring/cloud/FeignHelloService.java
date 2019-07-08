package xyz.ivyxjc.coderoad.spring.cloud;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Profile("feign")
@Service
@FeignClient(value = "service-hello", fallback = FeignHelloServiceHystrix.class)
public interface FeignHelloService {
    @GetMapping(value = "/hi")
    String sayHiFromClientOne(@RequestParam(value = "name") String name);
}
