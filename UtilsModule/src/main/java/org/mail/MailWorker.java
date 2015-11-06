package org.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailWorker extends Thread {

    Logger logger = LoggerFactory.getLogger(getClass());

    public void run() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        while (true) {
            final MailCompose mail = MailManagement.getInstance().getMail();

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(mail.getFrom(),
                                    mail.getPassword());
                        }
                    });

            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(mail.getFrom()));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(mail.getTo()));
                message.setSubject(mail.getTitle());
                message.setText(mail.getContent());

                Transport.send(message);

            } catch (MessagingException e) {
                e.printStackTrace();
                logger.error("sent mail error", e);
            }

        }
    }
}
