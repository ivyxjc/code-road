package xyz.ivyxjc.coderoad.spring.cloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("config-client")
@RestController
public class ConfigClientHelloController {
    @Value("${foo}")
    String foo;

    @RequestMapping(value = "/confighi")
    public String hi() {
        return foo;
    }
}
