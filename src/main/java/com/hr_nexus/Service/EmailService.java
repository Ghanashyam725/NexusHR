package com.hr_nexus.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.hr_nexus.Entity.EmalLogs;
import com.hr_nexus.Repository.EmailRepository;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepo;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendResetPasswordEmail(
            String recepientEmail,
            String token) {

        String resetLink =
                "http://localhost:6363/auth/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(recepientEmail);
        message.setSubject("Reset your password");
        message.setText(
                "Click the link to reset your password:\n" + resetLink);

        javaMailSender.send(message);
    }

    public String sendNotification(
            String recepientEmail,
            String subject,
            String body) {

        boolean sentStatus = false;

        try {

            MimeMessage msg = javaMailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(msg, true);

            helper.setTo(recepientEmail);
            helper.setSubject(subject);
            helper.setText(body, true);

            javaMailSender.send(msg);

            sentStatus = true;

        } catch (Exception e) {

            sentStatus = false;
        }

        EmalLogs logs =
                new EmalLogs(
                        recepientEmail,
                        subject,
                        body,
                        sentStatus);

        emailRepo.save(logs);

        return sentStatus
                ? "Email sent successfully"
                : "Email sending failed";
    }
}
