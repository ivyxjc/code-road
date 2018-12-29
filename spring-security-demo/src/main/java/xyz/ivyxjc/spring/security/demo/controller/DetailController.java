package xyz.ivyxjc.spring.security.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author Ivyxjc
 * @since 10/5/2018
 */
@Slf4j
@RestController
@RequestMapping("api")
public class DetailController {

    @RequestMapping("detail")
    public Principal detail(Principal user) {
        log.debug("detail: {}", user);
        log.debug("detail username: {}", user.getName());
        return user;
    }
}
