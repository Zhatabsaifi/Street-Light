package com.hu.Virtualize.services.login;

import com.hu.Virtualize.services.login.service.SendMail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
@Service
public class SendMailImpl implements SendMail {

    @Autowired
    private Environment env;

    /**
     * This function will send the mail to the user.
     * @param subject message subject
     * @param message  message content
     * @param to user email
     * @return status (true or false0
     */
    @Override
    public boolean sendEmail(String subject, String message, String to){
        boolean flag = false;

        String applicationEmail = env.getProperty("application.email");
        String applicationPassword = env.getProperty("application.password");

        //get the system properties
        Properties properties = System.getProperties();

        //Variable for gmail
        String host="smtp.gmail.com";

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.auth","true");

        //Step 1: to get the session object..
        Session session=Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(applicationEmail, applicationPassword);
            }



        });

        session.setDebug(true);
        MimeMessage m = new MimeMessage(session);

        try {
            m.setFrom(applicationEmail);
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            //adding subject to message
            m.setSubject(subject);
            m.setText(message);
            Transport.send(m);
            flag = true;

            log.info("Successfully send message to " + to);
        }catch (Exception e) {
            log.error(e.getMessage());
        }
        return flag;
    }
}
