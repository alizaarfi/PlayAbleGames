package com.example.service;

import com.example.dto.AccessibilityFeedbackDTO;
import jakarta.inject.Singleton;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

@Singleton
public class AccessibilityEmailService {

    // CHANGE THESE:
    private static final String ADMIN_EMAIL = "playablegames123@gmail.com";
    private static final String SMTP_USERNAME = "playablegames123@gmail.com";
    private static final String SMTP_PASSWORD = "dyvw vbmc tvey oyvu";

    public void sendFeedbackEmail(AccessibilityFeedbackDTO feedback) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTP_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(ADMIN_EMAIL));
            message.setSubject("Accessibility Feedback: " + feedback.getConcernType());

            String body = String.format(
                    "New Accessibility Feedback\n\n" +
                            "From: %s (%s)\n" +
                            "Type: %s\n\n" +
                            "Message:\n%s",
                    feedback.getName(),
                    feedback.getEmail(),
                    feedback.getConcernType(),
                    feedback.getMessage()
            );

            message.setText(body);
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }
}