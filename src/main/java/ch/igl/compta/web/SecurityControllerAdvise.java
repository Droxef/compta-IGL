package ch.igl.compta.web;

import java.security.Principal;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class SecurityControllerAdvise {

    @ModelAttribute("userName")
    String currentUser(Principal principal) {
        return (principal != null) ? principal.getName() : null;
    }

    @ModelAttribute("httpSession")
    HttpSession httpSession(HttpSession httpSession) {
        return httpSession;
    }
}
