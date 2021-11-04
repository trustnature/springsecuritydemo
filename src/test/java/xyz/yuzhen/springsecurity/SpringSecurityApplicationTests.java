package xyz.yuzhen.springsecurity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class SpringSecurityApplicationTests {

    @Test
    void contextLoads() {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("MD5", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("MD5"));
        encoders.put("noop", org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance());
        DelegatingPasswordEncoder encoder1 = new DelegatingPasswordEncoder("bcrypt", encoders);
        DelegatingPasswordEncoder encoder2 = new DelegatingPasswordEncoder("MD5", encoders);
        DelegatingPasswordEncoder encoder3 = new DelegatingPasswordEncoder("noop", encoders);
        String e1 = encoder1.encode("123");//{bcrypt}$2a$10$WwMslTprR.yf0cn8/1g4q.gPn57lOOUhU9BqTbY7gbNH4FwoX7t12
        String e2 = encoder2.encode("123");//{MD5}{bxND/q/nnGBdOkj+5BRjhBGlbNrIpT6MdBUJhCatmPs=}925e50269c792d3ab3b8fd9a9a7d264c
        String e3 = encoder3.encode("123");//{noop}123
        System.out.println("e1 = " + e1);
        System.out.println("e2 = " + e2);
        System.out.println("e3 = " + e3);
        System.out.println(encoder1.matches("123",e1));
        System.out.println(encoder2.matches("123",e2));
        System.out.println(encoder3.matches("123",e3));
    }
}
