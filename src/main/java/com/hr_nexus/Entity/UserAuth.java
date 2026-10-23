package com.hr_nexus.Entity;

import com.hr_nexus.ENUM.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_auth")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String userEmail;

    private String phone;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
