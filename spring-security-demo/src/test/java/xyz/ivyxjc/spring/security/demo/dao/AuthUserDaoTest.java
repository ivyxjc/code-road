package xyz.ivyxjc.spring.security.demo.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ivyxjc
 * @date 12/29/2018 10:03 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthUserDaoTest {
    private static final Logger log = LoggerFactory.getLogger(AuthUserDaoTest.class);
    @Autowired
    private AuthUserDao authUserDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testGetUser() {
        AuthUser authUser = authUserDao.getUser("user1");
        log.debug("authUser is: {}", authUser);
        boolean matched = passwordEncoder.matches("123", authUser.getPassword());
        Assert.assertEquals("user1", authUser.getUsername());
        Assert.assertTrue(matched);
    }
}
