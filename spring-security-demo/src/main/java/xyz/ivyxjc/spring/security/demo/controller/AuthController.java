package xyz.ivyxjc.spring.security.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.Principal;

/**
 * @author Ivyxjc
 * @since 10/17/2018
 */
@Slf4j
@RestController
@RequestMapping("api")
public class AuthController {

    @GetMapping("login")
    public Principal login(Principal user, Session.Cookie cookie, HttpSession session) {
        session.setMaxInactiveInterval(1000);
        log.debug("login cookie is: {}", cookie);
        return user;
    }
}
