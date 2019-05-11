package xyz.ivyxjc.spring.security.demo.dao;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author ivyxjc
 * @date 12/29/2018 10:25 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LogoutTest {

    private static final Logger log = LoggerFactory.getLogger(LogoutTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthUserDao authUserDao;

    @Test
    public void doSome() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(
            HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
            new MockSecurityContext(getPrincipal("user1")));
        int content = mockMvc.perform(get("/api/detail").session(session)).andReturn().getResponse()
            .getStatus();
        System.out.println("==============================");
        System.out.println(content);
    }

    private UsernamePasswordAuthenticationToken getPrincipal(String username) {

        AuthUser user = authUserDao.getUser(username);

        log.debug("get authUser result is: {}", user);
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                user,
                user.getPassword());
        log.debug("authentication is: {}", authentication.getPrincipal());
        return authentication;
    }

}

class MockSecurityContext implements SecurityContext {

    private Authentication authentication;

    public MockSecurityContext(Authentication authentication) {
        this.authentication = authentication;
    }

    @Override
    public Authentication getAuthentication() {
        return authentication;
    }

    @Override
    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }
}
