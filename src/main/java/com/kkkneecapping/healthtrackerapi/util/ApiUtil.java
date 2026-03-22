package com.kkkneecapping.healthtrackerapi.util;

import com.kkkneecapping.healthtrackerapi.entity.User;
import com.kkkneecapping.healthtrackerapi.exception.NoAuthorisedUserException;
import com.kkkneecapping.healthtrackerapi.security.SecurityUser;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Log4j2
public class ApiUtil {

  private ApiUtil() {}

  public static User getCurrentUser() {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
      return securityUser.getUser();
    } catch (Exception e) {
      log.error("Cound not get current user", e);
      throw new NoAuthorisedUserException();
    }
  }

  public static ResponseEntity<Void> responseOk() {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  public static <T> ResponseEntity<T> responseOk(T body) {
    return new ResponseEntity<>(body, HttpStatus.OK);
  }
}
