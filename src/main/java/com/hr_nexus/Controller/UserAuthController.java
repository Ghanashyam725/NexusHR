package com.hr_nexus.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr_nexus.DTO.AuthResponse;
import com.hr_nexus.DTO.ForgotPassword;
import com.hr_nexus.DTO.LoginRequest;
import com.hr_nexus.DTO.RegisterRequest;
import com.hr_nexus.DTO.ResetPassword;
import com.hr_nexus.Service.AuthServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user_auth")
public class UserAuthController {

    @Autowired
    private AuthServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> regiter(
            @RequestBody RegisterRequest register) {

        return ResponseEntity.ok(
                userService.register(register));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequest login) {

        return ResponseEntity.ok(
                userService.login(login));
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<String> forgotPassword(
            @RequestBody ForgotPassword forgot_pass_word) {

        userService.forgotPassword(forgot_pass_word);

        return ResponseEntity.ok(
                "Reset password link sent over on your registered email");
    }

    @PostMapping("/reset_password")
    public ResponseEntity<String> resetPassword(
            @RequestBody ResetPassword reset_password) {

        userService.resetPassword(reset_password);

        return ResponseEntity.ok(
                "Passwordreset successfully");
    }

    @PostMapping("/logged_out")
    public ResponseEntity<String> loggedOut(
            HttpServletRequest request) {

        return ResponseEntity.ok(
                userService.loggedOut(request));
    }
}
