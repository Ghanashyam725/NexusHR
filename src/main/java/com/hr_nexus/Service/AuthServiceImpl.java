package com.hr_nexus.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hr_nexus.DTO.AuthResponse;
import com.hr_nexus.DTO.ForgotPassword;
import com.hr_nexus.DTO.LoginRequest;
import com.hr_nexus.DTO.RegisterRequest;
import com.hr_nexus.DTO.ResetPassword;
import com.hr_nexus.Entity.UserAuth;
import com.hr_nexus.Repository.UserAuthRepository;
import com.hr_nexus.Security.JwtTokenUtil;
import com.hr_nexus.Security.TokenBlockService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserAuthRepository userRepo;

    @Autowired
    private JwtTokenUtil jwtToken;

    @Autowired
    private PasswordEncoder passwordEncode;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenBlockService tokenBlock;

    @Override
    public AuthResponse register(RegisterRequest register) {

        Optional<UserAuth> existUser =
                userRepo.findByUserEmail(register.userEmail);

        if (existUser.isPresent()) {
            throw new RuntimeException("User already exist");
        }

        UserAuth user = new UserAuth();

        user.setUserName(register.userName);
        user.setUserEmail(register.userEmail);
        user.setPhone(register.phone);
        user.setPassword(
                passwordEncode.encode(register.password));
        user.setRole(register.role);

        userRepo.save(user);

        String token = jwtToken.generateToken(user);

        return new AuthResponse(
                token,
                "User register successfully");
    }

    @Override
    public String login(LoginRequest login) {

        UserAuth user =
                userRepo.findByUserEmail(login.userEmail)
                        .orElseThrow(() ->
                                new RuntimeException("User not found"));

        if (!passwordEncode.matches(
                login.password,
                user.getPassword())) {

            throw new RuntimeException(
                    "Invalid credentials");
        }

        return jwtToken.generateToken(user);
    }

    @Override
    public void forgotPassword(
            ForgotPassword forgot_Pass_word) {

        UserAuth user =
                userRepo.findByUserEmail(
                                forgot_Pass_word.userEmail)
                        .orElseThrow(() ->
                                new RuntimeException("user not fond"));

        String token = UUID.randomUUID().toString();

        user.setResetToken(token);

        user.setResetTokenExpiry(
                new Date(
                        System.currentTimeMillis()
                                + 15 * 60 * 1000));

        userRepo.save(user);

        emailService.sendResetPasswordEmail(
                forgot_Pass_word.userEmail,
                token);
    }

    @Override
    public void resetPassword(
            ResetPassword reset_password) {

        UserAuth user =
                userRepo.findByResetToken(
                                reset_password.token)
                        .orElseThrow(() ->
                                new RuntimeException("Invalid token"));

        if (user.getResetTokenExpiry()
                .before(new Date())) {

            throw new RuntimeException(
                    "Token got expired ");
        }

        user.setPassword(
                passwordEncode.encode(
                        reset_password.newPassword));

        user.setResetToken(null);
        user.setResetTokenExpiry(null);

        userRepo.save(user);
    }

    @Override
    public String loggedOut(
            HttpServletRequest request) {

        String header =
                request.getHeader("Authorization");

        String token =
                jwtToken.extractToken(header);

        if (token != null) {
            tokenBlock.killingToken(token);
        }

        return "LoggedOut Successfully";
    }
}
