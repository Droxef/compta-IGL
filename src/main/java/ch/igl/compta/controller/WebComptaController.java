package ch.igl.compta.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ch.igl.compta.model.ComptaCompte;
import ch.igl.compta.model.ComptaCompteGroupe;
import ch.igl.compta.model.ComptaLine;
import ch.igl.compta.model.ComptaPlan;
import ch.igl.compta.service.web.ComptaServiceWeb;
import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/compta")
@SessionAttributes({"planActif", "planInfos", "comptables"})
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

    @GetMapping("/plan/comptes")
    public String getPlanComptes(HttpServletRequest request, Model model, RedirectAttributes attribute) {
        request.getSession().setAttribute("previousTab", "comptaPlanForm");
        ComptaPlan plan = (ComptaPlan)model.getAttribute("planActif");
        if(plan == null) {
            throw new IllegalStateException("No active plan in session");
        }
        request.getSession().setAttribute("previousObject", plan);
        List<ComptaLine> comptes = new ArrayList<>();
        comptes.addAll(service.getComptesByPlanId(plan.getId()));
        comptes.addAll(service.getGroupesByPlanId(plan.getId()));
        comptes.sort((m1, m2) -> StringUtils.compare(m1.getNumero().toString(), m2.getNumero().toString()));
        model.addAttribute("comptables", comptes);
        return "comptaPlanCompteForm";
    }

    @GetMapping("/plan/comptes/addCompte")
    public String addCompte(HttpServletRequest request, Model model, RedirectAttributes attribute) {
        ComptaCompte compte = new ComptaCompte();
        model.addAttribute("compte", compte);
        model.addAttribute("newCompte", true);
        return "comptaPlanCompteForm";
    }

    @GetMapping("/plan/comptes/compte/{id}")
    public String editCompte(HttpServletRequest request, Model model, @PathVariable final int id, RedirectAttributes attribute) {
        ComptaCompte compte = service.getCompteById(id);
        model.addAttribute("compte", compte);
        model.addAttribute("updateCompte", true);
        return "comptaPlanCompteForm";
    }


    @GetMapping("/plan/comptes/addGroupe")
    public String addGroupe(HttpServletRequest request, Model model, RedirectAttributes attribute) {
        ComptaCompteGroupe groupe = new ComptaCompteGroupe();
        model.addAttribute("groupe", groupe);
        model.addAttribute("newGroupe", true);
        return "comptaPlanCompteForm";
    }

    @GetMapping("/plan/comptes/groupe/{id}")
    public String editGroupe(HttpServletRequest request, Model model, @PathVariable final int id, RedirectAttributes attribute) {
        ComptaCompteGroupe groupe = service.getGroupeById(id);
        model.addAttribute("groupe", groupe);
        model.addAttribute("updateGroupe", true);
        return "comptaPlanCompteForm";
    }

    @PostMapping("/savePlan")
    public ModelAndView savePlan(Model model, @ModelAttribute ComptaPlan plan, BindingResult bresult, RedirectAttributes redirectAttribute) {
        plan = service.savePlan(plan);
        redirectAttribute.addFlashAttribute("planActif", plan);
        return new ModelAndView("redirect:/compta/plan");
    }

    @PostMapping("/plan/saveCompte")
    public ModelAndView saveCompte(Model model, @ModelAttribute ComptaCompte compte, BindingResult bresult, RedirectAttributes redirectAttribute) {
        ComptaPlan plan = (ComptaPlan) model.getAttribute("planActif");
        compte.setPlan(plan);
        compte = service.saveCompte(compte);
        return new ModelAndView("redirect:/compta/plan/comptes");
    }

    @PostMapping("/plan/saveGroupe")
    public ModelAndView saveGroupe(Model model, @ModelAttribute ComptaCompteGroupe groupe, BindingResult bresult, RedirectAttributes redirectAttribute) {
        ComptaPlan plan = (ComptaPlan) model.getAttribute("planActif");
        groupe.setPlan(plan);
        groupe = service.saveGroupe(groupe);
        return new ModelAndView("redirect:/compta/plan/comptes");
    }

    @GetMapping("/config")
    public String getConfigure(HttpServletRequest request, Model model, RedirectAttributes attribute) {
        return "comptaConfigure";
    }
    
}
