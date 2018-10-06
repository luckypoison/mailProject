package com.harry.mail.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloTest {
    @Autowired
    private HelloService helloService;

    @Autowired
    private MailService mailService;

    @Resource
    private TemplateEngine templateEngine;
    @Test
    public void sayHello(){
        helloService.sayHello();
    }

    @Test
    public void sendSimpleMailTest() throws Exception{
        mailService.sendSimpleMail("","","");
    }

    @Test
    public void sendHtmlMailTest() throws Exception{
        mailService.sendHtmlMail("","","");
    }

    @Test
    public void sendAttachment() throws Exception{

        mailService.sendOnlyAttachmentMail("","","","");
    }

    @Test
    public void sendImageMail() throws Exception{
        String srcId = "harryImage";
        String htmlContent = "<html><body><h3>this is a image:</h3> <img src = \'cid:{srcId}\'></body></html>".replace("{srcId}",srcId);
        mailService.sendImageMail("","",htmlContent,"",srcId);
    }
    @Test
    public  void sendTemplateMail() throws Exception{
        Map<String,Object> map =new HashMap<>();
        map.put("randomvalue",new Random().nextInt(999999));
        mailService.sendTemplateMail("","",map,"emailTemplate");
    }
}
