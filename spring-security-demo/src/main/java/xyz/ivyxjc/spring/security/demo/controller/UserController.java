package xyz.ivyxjc.spring.security.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author Ivyxjc
 * @since 9/23/2018
 */
@RestController
@RequestMapping("api")
public class UserController {

    @RequestMapping("user")
    public Principal user(Principal user) {
        return user;
    }
}
