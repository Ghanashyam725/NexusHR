package com.hr_nexus.Security;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.hr_nexus.ENUM.Permissions;
import com.hr_nexus.ENUM.Role;

public class RoleBasedPermissions {

    public static Map<Role, Set<Permissions>> getRoleBasedPermision() {

        Map<Role, Set<Permissions>> permission = new HashMap<>();

        // ADMIN Role
        permission.put(Role.ADMIN, new HashSet<Permissions>(Arrays.asList(
                Permissions.CREATE_EMPLOYEE,
                Permissions.UPDATE_EMPLOYEE,
                Permissions.VIEW_EMPLOYEE,
                Permissions.DELETE_EMPLOYEE,
                Permissions.UPLOAD_DOCUMENTS,

                Permissions.APPLY_LEAVE,
                Permissions.APPLY_WFH,
                Permissions.APPROVE_LEAVES,
                Permissions.APPROVE_WFH,
                Permissions.REJET_LEAVE,
                Permissions.REJECT_WFH,

                Permissions.CHECK_IN,
                Permissions.CHECK_OUT,
                Permissions.VIEW_ATTENDANCE,
                Permissions.MANGE_ATTENDANCE,

                Permissions.RUN_PAYROLL,
                Permissions.VIEW_PAYROLL,
                Permissions.DOWNLOAD_PAYSLIP,

                Permissions.CREATE_PERFERMANCE_GOAL,
                Permissions.REVIEW_EPLOYEE_PERFERMANCE,
                Permissions.VIEW_PERFERMANCE,

                Permissions.MANGE_USERS,
                Permissions.MANAGE_ROLES,
                Permissions.VIW_ANALYTICS,
                Permissions.MANAGE_PERMISSION
        )));

        // HR_MANAGER Role
        permission.put(Role.HR_MANAGER, new HashSet<Permissions>(Arrays.asList(
                Permissions.CREATE_EMPLOYEE,
                Permissions.UPDATE_EMPLOYEE,
                Permissions.VIEW_EMPLOYEE,

                Permissions.UPLOAD_DOCUMENTS,
                Permissions.APPLY_LEAVE,
                Permissions.APPLY_WFH,
                Permissions.REJET_LEAVE,
                Permissions.REJECT_WFH,

                Permissions.VIEW_LEAVES,
                Permissions.VIEW_ATTENDANCE,
                Permissions.MANGE_ATTENDANCE,
                Permissions.VIEW_PAYROLL,
                Permissions.DOWNLOAD_PAYSLIP,

                Permissions.REVIEW_EPLOYEE_PERFERMANCE,
                Permissions.VIEW_PERFERMANCE,
                Permissions.VIW_ANALYTICS
        )));

        // EMPLOYEE Role
        permission.put(Role.EMPLOYEE, new HashSet<Permissions>(Arrays.asList(
                Permissions.APPLY_LEAVE,
                Permissions.APPLY_WFH,
                Permissions.CHECK_IN,
                Permissions.CHECK_OUT,
                Permissions.VIEW_ATTENDANCE,
                Permissions.DOWNLOAD_PAYSLIP,
                Permissions.UPLOAD_DOCUMENTS,
                Permissions.VIEW_PERFERMANCE
        )));

        return permission;
    }
}
