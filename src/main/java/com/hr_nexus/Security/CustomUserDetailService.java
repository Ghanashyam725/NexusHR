package com.hr_nexus.Security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hr_nexus.ENUM.Permissions;
import com.hr_nexus.Entity.UserAuth;
import com.hr_nexus.Repository.UserAuthRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserAuthRepository userRepo;

    public UserDetails loadUserByUserEmail(String userEmail) {

        UserAuth user = userRepo.findByUserEmail(userEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        Set<Permissions> permissions =
                RoleBasedPermissions.getRoleBasedPermision()
                        .get(user.getRole());

        return new org.springframework.security.core.userdetails.User(
                user.getUserEmail(),
                user.getPassword(),
                permissions.stream()
                        .map(permission ->
                                new SimpleGrantedAuthority(permission.name()))
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        return loadUserByUserEmail(username);
    }
}
