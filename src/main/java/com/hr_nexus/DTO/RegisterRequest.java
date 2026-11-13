package com.hr_nexus.DTO;

import com.hr_nexus.ENUM.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    public String userName;
    public String userEmail;
    public String phone;
    public String password;
    public Role role;
}
