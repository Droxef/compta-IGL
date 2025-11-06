package ch.igl.compta.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ch.igl.compta.model.Personne;
import ch.igl.compta.service.api.PersonneService;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/v1/test/personnes")
public class PersonneController {

    // TODO change personne by personneDTO and add mapper

    @Autowired
    private PersonneService personneService;

    @GetMapping("")
    public Iterable<Personne> getPersonnes() {
        return personneService.getPersonnes();
    }

    @PostMapping("")
    public Personne addPersonne(@Valid @RequestBody Personne personne, BindingResult results) {
        if(results.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, results.getAllErrors().get(0).getDefaultMessage());
        }
        if(personne.getId() != null && personne.getId() > 0) {
            // Error because personne is already saved
            if(personneService.getPersonneById(personne.getId()) != null) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Data exist already");
            }
        }
        if(!personne.validateData()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect Data");
        }
        if(!personneService.validateBusiness(personne)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect Data");
        }
        try {
            Personne personneSaved = personneService.createPersonne(personne);
            return personneSaved;
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Personne deletePersonne(@PathVariable("id") Long id) {
        return personneService.deletePersonneById(id);
    }

    @PutMapping("/{id}")
    public Personne modifyPersonne(@PathVariable String id, @RequestBody Personne entity) {
        //TODO: process PUT request
        
        return entity;
    }

    @GetMapping("/{id}")
    public Personne getPersonne(@PathVariable("id") Long id) {
        return personneService.getPersonneById(id);
    }

    @GetMapping("/search")
    public List<Personne> searchPerson(@RequestParam(required=false) String param) {
        return new ArrayList<>();
    }
    
    

}
