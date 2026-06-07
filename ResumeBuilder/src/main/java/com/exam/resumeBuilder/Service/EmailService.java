package com.exam.resumeBuilder.Service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    @Value("${spring.mail.properties.mail.smtp.from}")
    private String fromEmail;
    private final JavaMailSender javaMailSender;

    public void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        log.info("Inside EmailService - sendHtml services: {},{},{}",to, subject, htmlContent);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new  MimeMessageHelper(mimeMessage, true,"UTF-8");
        mimeMessageHelper.setFrom(fromEmail);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(htmlContent, true);
        javaMailSender.send(mimeMessage);
    }
    public void sendEmailWithAttachment(String to, String subject, String body, byte[] attachment,  String fileName) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(fromEmail);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(body);
        mimeMessageHelper.addAttachment(fileName, new ByteArrayResource(attachment));
        javaMailSender.send(mimeMessage);
    }
}
