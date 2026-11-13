package com.hr_nexus.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr_nexus.Service.EmailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailLogsController {

    @Autowired
    private EmailService emailService;

    @PostMapping(
            "/sent_ResetPassword_Link/{recepientEmail}/{token}")
    public ResponseEntity<String> sendResetPasswordEmail(
            @PathVariable String recepientEmail,
            @PathVariable String token) {

        emailService.sendResetPasswordEmail(
                recepientEmail,
                token);

        return ResponseEntity.ok(
                "Sent reset password linkover on your registerd email");
    }

    @PostMapping(
            "/notifications/{recepientEmail}/{subject}/{body}")
    public ResponseEntity<String> notify(
            @PathVariable String recepientEmail,
            @PathVariable String subject,
            @PathVariable String body) {

        emailService.sendNotification(
                recepientEmail,
                subject,
                body);

        return ResponseEntity.ok(
                "Notification send successfully");
    }
}
