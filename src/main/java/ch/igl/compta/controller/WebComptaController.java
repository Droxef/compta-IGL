package ch.igl.compta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/compta")
public class WebComptaController {

    @GetMapping("")
    public String getCompta(HttpServletRequest request, Model model, RedirectAttributes attribute) {
        return "comptaForm";
    }

    @GetMapping("/search")
    public String getSearch(HttpServletRequest request, Model model, RedirectAttributes attribute) {
        return "comptaSearch";
    }

    @GetMapping("/plan")
    public String getPlans(HttpServletRequest request, Model model, RedirectAttributes attribute) {
        return "comptaPlanForm";
    }

    @GetMapping("/config")
    public String getConfigure(HttpServletRequest request, Model model, RedirectAttributes attribute) {
        return "comptaConfigure";
    }
    
}
