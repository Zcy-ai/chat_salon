package com.sr03.chat_salon.web;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * ClassName:SpringBootController
 * Package:com.bjpowernode.springboot.web
 * Description:<br/>
 */
@Controller
public class SpringBootController {
    @RequestMapping(value = "/springBoot/say")
    public @ResponseBody String say() {
        return "Hello,springBoot!";
    } }