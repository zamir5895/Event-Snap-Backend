package com.backend.users.Email.Service;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class EmailListener {

    @Autowired
    EmailService emailService;
    @EventListener
    @Async
    public void handleSendVerificationCodeEmail( CodeVerificationEvent event ) throws MessagingException {
        LocalDateTime time =event.getExpiryDate().toLocalDateTime();
        String subject = "Account verification for " + event.getFirstName() + " " + event.getLastName();
        String verificationCode = "VERIFICATION CODE " + event.getCode();
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
                + "<p style=\"font-size: 12px; font-weight: bold; color:black\"> You have 2 minutes. Vence en " +  formatTime(time) + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        emailService.sendEmail(event.getEmail(), subject,htmlMessage);
    }


    public String formatTime(LocalDateTime time){

        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return time.format(formatter);
    }
}


