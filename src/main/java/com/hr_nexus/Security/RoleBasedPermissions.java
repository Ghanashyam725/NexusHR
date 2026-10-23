package com.hr_nexus.Security;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.hr_nexus.ENUM.Permissions;
import com.hr_nexus.ENUM.Role;

public class RoleBasedPermissions {

    public static Map<Role, Set<Permissions>> getRoleBasedPermission() {

        Map<Role, Set<Permissions>> permission = new HashMap<>();

        // ADMIN Role
        permission.put(
                Role.ADMIN,
                new HashSet<Permissions>(
                        Arrays.asList(
                                Permissions.CREATE_EMPLOYEE
                        )
                )
        );

        return permission;
    }
}
