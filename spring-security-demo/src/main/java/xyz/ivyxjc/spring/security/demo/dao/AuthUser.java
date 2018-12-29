package xyz.ivyxjc.spring.security.demo.dao;

import lombok.Data;

/**
 * @author Ivyxjc
 * @since 9/24/2018
 */
@Data
public class AuthUser {
    private long userId;
    private String username;
    private String password;
}

