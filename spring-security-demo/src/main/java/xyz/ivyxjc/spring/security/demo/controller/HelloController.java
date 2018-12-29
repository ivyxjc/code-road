package xyz.ivyxjc.spring.security.demo.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ivyxjc
 * @since 9/23/2018
 */
@RestController
@RequestMapping("api")
public class HelloController {

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public Home home() {
        return new Home("hello");
    }

    @Data
    @AllArgsConstructor
    class Home {
        private String name;
    }
}
