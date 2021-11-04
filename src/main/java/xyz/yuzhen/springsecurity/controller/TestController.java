package xyz.yuzhen.springsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class TestController {

    @PostMapping("/success")
    public String success(){
        return "success";
    }

    @PostMapping("/fail")
    public String fail() {
        return "fail";
    }

    @GetMapping("find")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('menu:system')")
    public String add(){
        return "Hello world";
    }

    @GetMapping("findAll")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_管理员')")
    public String index() {return "findAll";}

    @PostMapping("doLogins")
    @ResponseBody
    public String login() {
        return "SUCCESS";
    }
}
