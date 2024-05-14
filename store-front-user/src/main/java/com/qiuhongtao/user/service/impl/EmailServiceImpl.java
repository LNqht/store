package com.qiuhongtao.user.service.impl;

import com.qiuhongtao.user.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.BodyPart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    /**
     * 邮件发送
     */
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String tofrom;

    @Override
    public boolean sendEmail(String toEmail, String text, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //设置发件邮箱
        simpleMailMessage.setFrom(tofrom);
        //收件人邮箱
        simpleMailMessage.setTo(toEmail);

        //

        MimeMultipart multipart = new MimeMultipart("related");
        BodyPart messageBodyPart = new MimeBodyPart();


        //

        //主题标题
        simpleMailMessage.setSubject(text);
        //信息内容
        simpleMailMessage.setText(message);
        //执行发送
        try {   //发送可能失败
            javaMailSender.send(simpleMailMessage);
            //没有异常返回true，表示发送成功
            return true;
        }catch (Exception e) {
            //发送失败，返回false
            e.printStackTrace();
            return false;
        }
    }
}
