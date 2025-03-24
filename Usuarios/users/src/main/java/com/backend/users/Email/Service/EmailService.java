package com.backend.users.Email.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendEmail(String to, String subject, String content) throws MessagingException {
        try{
            testConnection();
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            javaMailSender.send(mimeMessage);
        } catch (MailAuthenticationException e) {
            System.err.println("Error de autenticación: " + e.getMessage());
        } catch (MessagingException e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
        }

    }
    private void testConnection() {
        if (javaMailSender instanceof JavaMailSenderImpl) {
            JavaMailSenderImpl mailSenderImpl = (JavaMailSenderImpl) javaMailSender;
            try {
                mailSenderImpl.testConnection();
                System.out.println("Conexión al servidor SMTP exitosa.");
            } catch (MessagingException e) {
                throw new MailAuthenticationException("No se pudo conectar al servidor SMTP: " + e.getMessage());
            }
        }
    }
}
