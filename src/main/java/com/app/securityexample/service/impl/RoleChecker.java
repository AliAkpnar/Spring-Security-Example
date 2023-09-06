package com.app.securityexample.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class RoleChecker
{
    public boolean checkRole(String role1, String role2){
        Collection<? extends GrantedAuthority> authorities =
            SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        long roleCount = authorities.stream()
            .filter(grantedAuthority -> grantedAuthority.toString().equals(role1) || grantedAuthority.toString().equals(role2))
            .count();
        return roleCount == 2;
    }
}
