package ch.igl.compta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ch.igl.compta.model.Personne;
import ch.igl.compta.service.web.PersonneServiceWeb;
import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/personne")
@SessionAttributes("personneList")
public class WebPersonneController {


    @Autowired
    private PersonneServiceWeb personneService;

    @ModelAttribute("personneList")
    public Iterable<Personne> personneList() {
        return personneService.getAllPersonnes();
    }

    @GetMapping("")
    public String getPersonne(HttpServletRequest request, Model model, RedirectAttributes attribute) {
        Iterable<Personne> personnes = personneService.getAllPersonnes();
        model.addAttribute("personnes", personnes);
        // TODO understand this (only for redirect i think)
        attribute.addAttribute("personneList", personnes);
        request.getSession().setAttribute("personneList", personnes);
        return "personForm";
    }

    @GetMapping("/deletePersonne/{id}")
    public ModelAndView deletePersonne(@PathVariable("id") final int id) {
        personneService.deletePersonne(id);
        return new ModelAndView("redirect:/personne");
    }

    @PostMapping("/savePersonne")
    public ModelAndView saveEmployee(@ModelAttribute Personne personne, BindingResult bresult, RedirectAttributes redirectAttribute) {
        personneService.savePersonne(personne);
        return new ModelAndView("redirect:/personne");
    }
    
    @GetMapping("/createPersonne")
	public String createEmployee(HttpServletRequest request, Model model, @ModelAttribute List<Personne> personneList) {
        if(request.getSession().getAttribute("personneList") != null) {
            model.addAttribute("personnes", request.getSession().getAttribute("personneList"));
            request.getSession().removeAttribute("personneList");
        } else {
            model.addAttribute("personnes", personneList);
        }
		Personne p = new Personne();
		model.addAttribute("person", p);
        model.addAttribute("newPerson", true);
		return "personForm";
	}
}