package com.kkkneecapping.healthtrackerapi.security;

import com.kkkneecapping.healthtrackerapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginEventListener {

  private final UserService userService;

  @EventListener
  public void handleAuthenticationSuccess(AuthenticationSuccessEvent event) {
    Object principal = event.getAuthentication().getPrincipal();
    if (principal instanceof SecurityUser securityUser) {
      userService.updateLastLogin(securityUser.getUser());
    }
  }
}
