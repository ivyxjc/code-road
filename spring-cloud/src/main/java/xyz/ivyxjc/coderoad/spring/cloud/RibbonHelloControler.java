package xyz.ivyxjc.coderoad.spring.cloud;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Profile("ribbon")
@RestController
public class RibbonHelloControler {
    @Autowired
    RibbonHelloService ribbonHelloService;

    @GetMapping(value = "/hi")
    public String hi(@RequestParam String name) {
        return ribbonHelloService.hiService(name);
    }
}
