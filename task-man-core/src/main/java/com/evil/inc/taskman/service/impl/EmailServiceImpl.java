package com.evil.inc.taskman.service.impl;

import com.evil.inc.taskman.entity.Email;
import com.evil.inc.taskman.service.EmailService;
import lombok.extern.slf4j.Slf4j;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
public class EmailServiceImpl implements EmailService {

    public static final String FROM_EMAIL_ADDRESS = "evil.inc.taskman@gmail.com";
    public static EmailServiceImpl INSTANCE;

    public static EmailServiceImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EmailServiceImpl();
        }

        return INSTANCE;
    }

    @Override
    public void sendEmail(final Email email) {
        Session session = Session.getInstance(getServerProperties(), getAuthenticator());

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL_ADDRESS));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getRecipient()));
            message.setSubject(email.getSubject());
            message.setContent(email.getContent(), "text/html");

            Transport.send(message);

        } catch (MessagingException e) {
            log.info(e.getMessage(), e);
        }
    }

    private Authenticator getAuthenticator() {
        final String username = "evil.inc.taskman@gmail.com";
        final String password = "tblkthsusqkmqpag";

        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
    }

    private Properties getServerProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        return props;
    }
}
