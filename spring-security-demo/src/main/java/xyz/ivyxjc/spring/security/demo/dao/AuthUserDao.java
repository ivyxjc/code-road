package xyz.ivyxjc.spring.security.demo.dao;

/**
 * @author Ivyxjc
 * @since 9/24/2018
 */
public interface AuthUserDao {

    AuthUser getUser(String username);
}
