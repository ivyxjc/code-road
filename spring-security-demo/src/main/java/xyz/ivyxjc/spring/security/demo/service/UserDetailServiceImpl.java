package xyz.ivyxjc.spring.security.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.ivyxjc.spring.security.demo.dao.AuthUser;
import xyz.ivyxjc.spring.security.demo.dao.AuthUserDao;

/**
 * @author Ivyxjc
 * @since 9/23/2018
 */
@Slf4j
@Service("customUserDetail")
public class UserDetailServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final AuthUserDao authUserDao;

    @Autowired
    public UserDetailServiceImpl(PasswordEncoder passwordEncoder, AuthUserDao authUserDao) {
        Assert.notNull(passwordEncoder, "passwordEncoder should not be null");
        Assert.notNull(authUserDao, "authUserDao should not be null");
        this.passwordEncoder = passwordEncoder;
        this.authUserDao = authUserDao;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (StringUtils.isBlank(s)) {
            return null;
        }
        AuthUser authUser = authUserDao.getUser(s);
        log.debug("load user: {}", authUser);
        UserDetails user =
                User.withUsername(authUser.getUsername())
                        .password(authUser.getPassword())
                        .roles("USER")
                        .build();
        log.debug("load userdetail: {}", user);
        return user;
    }
}
