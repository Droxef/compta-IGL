package ch.igl.compta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import ch.igl.compta.model.ComptaPlan;
import ch.igl.compta.service.web.ComptaServiceWeb;


@Controller
@RequestMapping("/compta")
@SessionAttributes({"planActif", "planInfos"})
public class WebComptaController {

    @Autowired
    private ComptaServiceWeb service;

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
        if(model.getAttribute("planActif") == null) {
            model.addAttribute("planActif", service.getLastPlanActif());
        }
        model.addAttribute("planInfos", service.getPlansInfo());
        return "comptaPlanForm";
    }

    @PostMapping("/plan/change")
    public ModelAndView changePlan(HttpServletRequest request, Model model, @RequestParam("planId") final long planId, RedirectAttributes attribute) {
        ComptaPlan plan = service.getPlanById(planId);
        attribute.addFlashAttribute("planActif", plan);
        return new ModelAndView("redirect:/compta/plan");
    }

    @GetMapping("/plan/create")
    public String createPlan(HttpServletRequest request, Model model, RedirectAttributes attribute) {
        ComptaPlan plan = new ComptaPlan();
        model.addAttribute("plan", plan);
        model.addAttribute("newPlan", true);
        return "comptaPlanForm";
    }

    @GetMapping("/plan/close")
    public String closePlan(HttpServletRequest request, Model model, RedirectAttributes attribute) {
        ComptaPlan plan = (ComptaPlan) model.getAttribute("planActif");
        plan = service.closePlan(plan);
        attribute.addFlashAttribute("planActif", plan);
        return "redirect:/compta/plan";
    }

    @GetMapping("/plan/delete")
    public String deletePlan(HttpServletRequest request, Model model, RedirectAttributes attribute) {
        ComptaPlan plan = (ComptaPlan) model.getAttribute("planActif");
        if(plan == null || plan.getId() == null) {
            model.addAttribute("errorMessage", "Impossible de supprimer un plan non enregistré.");
            attribute.addFlashAttribute("planActif", plan);
        } else {
            service.deletePlan(plan.getId());
            model.addAttribute("planActif", service.getLastPlanActif());
            attribute.addFlashAttribute("planActif", service.getLastPlanActif());
        }
        return "redirect:/compta/plan";
    }

    @PostMapping("/savePlan")
    public ModelAndView savePlan(Model model, @ModelAttribute ComptaPlan plan, BindingResult bresult, RedirectAttributes redirectAttribute) {
        plan = service.savePlan(plan);
        redirectAttribute.addFlashAttribute("planActif", plan);
        return new ModelAndView("redirect:/compta/plan");
    }

    @GetMapping("/config")
    public String getConfigure(HttpServletRequest request, Model model, RedirectAttributes attribute) {
        return "comptaConfigure";
    }
    
}
