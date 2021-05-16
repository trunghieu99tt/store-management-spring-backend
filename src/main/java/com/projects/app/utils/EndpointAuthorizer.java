package com.projects.app.utils;

import com.projects.app.common.exception.model.BackendError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service("EndpointAuthorizer")
@Slf4j
public class EndpointAuthorizer {
    public boolean authorizer(List<String> roles) throws BackendError {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            throw new BackendError(HttpStatus.UNAUTHORIZED, "Not allow");
        }

        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication.getAuthorities();
        if (roles.size() == 0) {
            return true;
        }
        for (GrantedAuthority authority : authorities) {
            for (String role : roles) {
                if (authority.getAuthority().equals(role.toString())) {
                    return true;
                }
            }
        }
        return false;
    }
}
