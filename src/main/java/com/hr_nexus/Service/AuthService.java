package com.hr_nexus.Service;

import com.hr_nexus.DTO.AuthResponse;
import com.hr_nexus.DTO.ForgotPassword;
import com.hr_nexus.DTO.LoginRequest;
import com.hr_nexus.DTO.RegisterRequest;
import com.hr_nexus.DTO.ResetPassword;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest register);

    String login(LoginRequest login);

    void forgotPassword(ForgotPassword forgot_Pass_word);

    void resetPassword(ResetPassword reset_password);

    String loggedOut(HttpServletRequest request);
}
