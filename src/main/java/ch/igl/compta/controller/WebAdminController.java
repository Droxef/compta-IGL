package ch.igl.compta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class WebAdminController {

    @GetMapping("/")
    public String home(Model model) {
        return "adminHome";
    }

    @GetMapping("/users")
    public String userManagement(Model model) {
        return "adminUsers";
    }

    @GetMapping("/roles")
    public String roleManagement(Model model) {
        return "adminRoles";
    }
}
