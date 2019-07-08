package xyz.ivyxjc.coderoad.spring.cloud;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Profile("ribbon")
@Service
public class RibbonHelloService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "hiError")
    public String hiService(String name) {
        return restTemplate.getForObject("http://SERVICE-HELLO/hi?name=" + name, String.class);
    }

    public String hiError(String name) {
        return "hi, name, sorry, sorry";
    }

}
