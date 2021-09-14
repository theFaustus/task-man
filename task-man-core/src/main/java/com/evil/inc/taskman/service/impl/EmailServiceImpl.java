package com.evil.inc.taskman.service.impl;

import com.evil.inc.taskman.entity.Email;
import com.evil.inc.taskman.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EmailServiceImpl implements EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
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
        final Properties props = getServerProperties();
        Session session = Session.getInstance(props, getAuthenticator(props.getProperty("gmail.user"),
                                                                      props.getProperty("gmail.pass")));

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

    private Authenticator getAuthenticator(String username, String password) {
        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
    }

    private Properties getServerProperties() {
        Properties props = new Properties();
        try (InputStream resourceAsStream = UserServiceImpl.class.getClassLoader().getResourceAsStream(
                "mail.properties")) {
            props.load(resourceAsStream);
        } catch (IOException e) {
            log.trace("Oops, something went wrong during reading mail.properties {}", e.getMessage());
        }
        return props;
    }
}
