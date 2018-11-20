package com.frg.springbatch.common;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

import static org.apache.velocity.app.Velocity.getTemplate;

@Service
public class SendMail {

    private static final Logger log = LoggerFactory.getLogger(SendMail.class);

    @Autowired
    private JavaMailSender mailSender;

    private VelocityEngine engine;

    @Value("${codeurjc.batch.notifications.email}")
    private String sender;

    @Value("${codeurjc.batch.attachment}")
    private String attachment;


    private boolean sent;

    public SendMail() {
    }

    public boolean createMail(String attachment) throws MessagingException {

        mailSender = new JavaMailSenderImpl();
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        engine = new VelocityEngine();
        engine.init();

        Map<String, Object> model = new HashMap<>();
        model.put("name", "Samuel");
        model.put("code", attachment);
        helper.setFrom("notifyappsfrg@gmail.com");
        helper.setTo("notifyappsfrg@gmail.com");
        //helper.setCc("frgar86");

        helper.setSubject(VelocityEngineUtils.mergeTemplateIntoString(engine, "email-subject.vm", "UTF-8", model));
        helper.setText(VelocityEngineUtils.mergeTemplateIntoString(engine, "email-body.vm", "UTF-8", model));

        log.info("Preparing message for be sent!");

        FileSystemResource file = new FileSystemResource(attachment);
        helper.addAttachment(file.getFilename(), file);



        return  sent;
    }
}
