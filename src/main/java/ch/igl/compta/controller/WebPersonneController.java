package ch.igl.compta.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpSessionRequiredException;
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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/personne")
@SessionAttributes("personnes")
public class WebPersonneController {


    @Autowired
    private PersonneServiceWeb personneService;

    @ModelAttribute("personnes")
    public Iterable<Personne> personneList() {
        return new ArrayList<>(); // personneService.getAllPersonnes();
    }

    @GetMapping("")
    public String getPersonne(HttpServletRequest request, Model model, RedirectAttributes attribute) throws HttpSessionRequiredException {
        String jwt = (String) request.getSession().getAttribute("jwt");

        if (jwt == null) {
            jwt = Arrays.stream(request.getCookies())
                .filter(c -> "X-Auth-Token".equals(c.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
        }

        if (jwt == null) {
            throw new HttpSessionRequiredException("JWT manquant");
        }

        Iterable<Personne> personnes = personneService.getAllPersonnes(jwt);
        model.addAttribute("personnes", personnes);
        // TODO understand this (only for redirect i think)
        attribute.addAttribute("personnes", personnes);
        //request.getSession().setAttribute("personneList", new ArrayList<Personne>((ArrayList) personnes));
        return "personForm";
    }

    @GetMapping("/deletePersonne/{id}")
    public ModelAndView deletePersonne(@PathVariable("id") final int id) {
        personneService.deletePersonne(id);
        return new ModelAndView("redirect:/personne");
    }

    @PostMapping("/savePersonne")
    public ModelAndView saveEmployee(Model model, @ModelAttribute Personne personne, HttpServletRequest request, BindingResult bresult, RedirectAttributes redirectAttribute) throws HttpSessionRequiredException {
        String jwt = (String) request.getSession().getAttribute("jwt");

        if (jwt == null) {
            jwt = Arrays.stream(request.getCookies())
                .filter(c -> "X-Auth-Token".equals(c.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
        }

        if (jwt == null) {
            throw new HttpSessionRequiredException("JWT manquant");
        }

        personneService.savePersonne(jwt, personne);
        return new ModelAndView("redirect:/personne");
    }
    
    @GetMapping("/createPersonne")
	public String createEmployee(HttpServletRequest request, Model model, @ModelAttribute Iterable<Personne> personnes, RedirectAttributes attribute) {
        if(personnes == null || !(personnes instanceof ArrayList)) {
//            personnes = (Iterable<Personne>) model.getAttribute("personnes");
        }
        /*        if(request.getSession().getAttribute("personneList") != null) {
            model.addAttribute("personnes", request.getSession().getAttribute("personneList"));
            request.getSession().removeAttribute("personneList");
        } else {*/
//            model.addAttribute("personnes", personnes);
//        }
		Personne p = new Personne();
		model.addAttribute("person", p);
        model.addAttribute("newPerson", true);
		return "personForm";
	}
    
    @GetMapping("/updatePersonne/{id}")
	public String createEmployee(HttpServletRequest request, Model model, @ModelAttribute List<Personne> personneList, @PathVariable("id") final int id) {
/*        if(request.getSession().getAttribute("personneList") != null) {
            model.addAttribute("personnes", request.getSession().getAttribute("personneList"));
            request.getSession().removeAttribute("personneList");
        } else {
            model.addAttribute("personnes", personneList);
        }*/
		Personne p = personneService.getPersonne(id);
		model.addAttribute("person", p);
        model.addAttribute("updatePerson", true);
		return "personForm";
	}
}