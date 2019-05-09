package xyz.ivyxjc.coderoad.spring.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import xyz.ivyxjc.coderoad.spring.transaction.model.Ext;
import xyz.ivyxjc.coderoad.spring.transaction.model.Trans;
import xyz.ivyxjc.coderoad.spring.transaction.service.TransService;

@SpringBootApplication
@PropertySource(value = {"classpath:application.yml", "classpath:jdbc.properties"})
public class BootRunner {

    public static void main(String[] args) {
        SpringApplication.run(BootRunner.class);
    }

    @Component
    private class CustomRunner implements CommandLineRunner {
        @Autowired
        private TransService transService;

        @Override
        public void run(String... args) throws Exception {
            Trans trans = new Trans(UUID.randomUUID().toString().substring(0, 20), "abcdef", new BigDecimal(20),
                LocalDateTime.now());
            Ext ext = new Ext(UUID.randomUUID().toString().substring(0, 20), "bcdefgh", new BigDecimal(30),
                LocalDateTime.now());
            int res = transService.insert(trans, ext);
            System.out.println("+++++++++++");
            System.out.println(res);
        }
    }

}
