package com.harry.mail.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@Service
public class MailServiceImpl implements MailService{

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailSender sender;

    @Resource
    private TemplateEngine templateEngine;

    //这是发送一个文本邮件
    public void sendSimpleMail(String to,String subject,String content) throws Exception{
        SimpleMailMessage mail =new SimpleMailMessage();
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(content);
        mail.setFrom(from);
        sender.send(mail);
    }

    //发送html邮件
    public void sendHtmlMail(String to , String subject , String content) throws Exception{
        MimeMessage message =mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper =new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    //带有附件的邮件
    public void sendOnlyAttachmentMail(String to ,String subject,String content , String filePath) throws Exception{

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);
            FileSystemResource file =new FileSystemResource(new File(filePath));
            String fileName = file.getFilename();
            helper.addAttachment(fileName,file);
            helper.addAttachment(fileName+"_test",file);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    //带有多个附件的邮件
    public void sendAttachmentsMail(String to , String subject, String content , List<String> filePathList) throws Exception{

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);
            FileSystemResource file = null;
            for(String filePath : filePathList){
                file =new FileSystemResource(new File(filePath));
                String fileName = file.getFilename();
                helper.addAttachment(fileName,file);
                helper.addAttachment(fileName+"_test",file);
            }

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    //带有图片的邮件
    public void sendImageMail(String to ,String subject,String content , String filePath,String srcId) throws Exception{

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);
            FileSystemResource file =new FileSystemResource(new File(filePath));
            helper.addInline(srcId,file);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    //携带多个图片的邮件
    public void sendImagesMail(String to ,String subject,String content , List<String> filePathList,List<String> srcIdList) throws Exception{

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);
            for(int i =0;i<srcIdList.size();i++){
                FileSystemResource file =new FileSystemResource(new File(filePathList.get(i)));
                helper.addInline(srcIdList.get(i),file);
            }

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    //使用html模版进行发送邮件
    public void sendTemplateMail(String to , String subject, Map<String,Object> map , String templateName) throws Exception{
        Context context = new Context();
        Set<String> keyList =map.keySet();
        for(String key:keyList){
            context.setVariable(key, map.get(key));
        }
        String templateContext = templateEngine.process(templateName,context);
        sendHtmlMail(to,subject,templateContext);
    }
}
