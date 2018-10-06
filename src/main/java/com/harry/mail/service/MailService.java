package com.harry.mail.service;

import java.util.List;
import java.util.Map;

public interface MailService {
    //这是发送一个文本邮件
    public void sendSimpleMail(String to,String subject,String content) throws Exception;

    //发送html邮件
    public void sendHtmlMail(String to , String subject , String content) throws Exception;

    //带有附件的邮件
    public void sendOnlyAttachmentMail(String to ,String subject,String content , String filePath) throws Exception;

    //带有多个附件的邮件
    public void sendAttachmentsMail(String to , String subject, String content , List<String> filePathList) throws Exception;


    //带有图片的邮件
    public void sendImageMail(String to ,String subject,String content , String filePath,String srcId) throws Exception;

    //携带多个图片的邮件
    public void sendImagesMail(String to ,String subject,String content , List<String> filePathList,List<String> srcIdList) throws Exception;

    //读取html文件作为模版
    public void sendTemplateMail(String to , String subject, Map<String,Object> map , String templateName) throws Exception;
}
