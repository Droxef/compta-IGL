package ch.igl.compta.controller;

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

import ch.igl.compta.model.ComptaCompte;
import ch.igl.compta.model.ComptaEntity;
import ch.igl.compta.model.ComptaPlan;
import ch.igl.compta.service.api.ComptaService;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/v1/test/compta")
public class ComptaController {

    @Autowired
    private ComptaService comptaService;

    ///////// Lines /////////////

    @GetMapping("")
    public Iterable<ComptaEntity> getLines() {
        return comptaService.getLines();
    }

    @GetMapping("/byYear/{year}")
    public Iterable<ComptaEntity> getLinesByYear(@PathVariable int year) {
      return comptaService.getLinesByYear(year);
      // throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Not yet finished");
    }
    
    @GetMapping("/byPlan")
    public Iterable<ComptaEntity> getLinesByYear(@RequestParam String name) {
      // return comptaService.getLinesByPlanName(name);
      throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Not yet finished");
    }

    ////////// Plan /////////////////
    @GetMapping("/plan")
    public Iterable<ComptaPlan> getPlans() {
        return comptaService.getPlans();
    }
  
    @GetMapping("/plan/{id}")
    public ComptaPlan getPlanById(@PathVariable int id) {
        return comptaService.getPlanById(id);
    }
  
    @GetMapping("/plan/get")
    public ComptaPlan getPlanByYear(@RequestParam(required=false) Integer year, @RequestParam(required=false) String name) {
      if(year != null) {
        return comptaService.getPlanByYear(year);
      } else if(name != null) {
        return comptaService.getPlanByName(name);
      } else {
        return null;
      }
    }

    @GetMapping("/plan/search")
    public Iterable<ComptaPlan> getPlanByName(@RequestParam String name) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Not yet finished");
    }

    @PostMapping("/plan")
    public ComptaPlan addPlan(@Valid @RequestBody ComptaPlan plan, BindingResult results) {
        if(results.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, results.getAllErrors().get(0).getDefaultMessage());
        }
        if(plan.getId() != null && plan.getId() > 0) {
            // Error because personne is already saved
            if(comptaService.getPlanById(plan.getId()) != null) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Data exist already");
            }
        }
        if(!plan.validateData()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect Data");
        }
        if(!comptaService.validateBusiness(plan)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect Data");
        }
        try {
            ComptaPlan planSaved = comptaService.createPlan(plan);
            return planSaved;
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    
    @PutMapping("plan/{id}")
    public ComptaPlan savePlan(@PathVariable int id, @Valid @RequestBody ComptaPlan plan) {
        ComptaPlan oldPlan = comptaService.getPlanById(id);
        // TODO map plan to oldPlan
        return comptaService.updatePlan(oldPlan);
    }

    @DeleteMapping("plan/{id}")
    public ComptaPlan deletePlan(@PathVariable int id) {
      // TODO check if exist + if can delete
      return comptaService.deletePlan(id);
    }

    @GetMapping("plan/{id}/compte")
    public Iterable<ComptaCompte> getComptesByPlan(@PathVariable int id) {
        ComptaPlan plan = comptaService.getPlanById(id);
        return plan.getComptes();
    }
    

    @PostMapping("plan/{id}/addCompte")
    public ComptaCompte postMethodName(@PathVariable int id, @Valid @RequestBody ComptaCompte compte, BindingResult results) {
        if(results.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, results.getAllErrors().get(0).getDefaultMessage());
        }
        if(id < 0 || comptaService.getPlanById(id) == null) {
            // Error because plan nopt exist
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Plan does not exist");
        }
        ComptaPlan plan = comptaService.getPlanById(id);
        if(compte.getId() != null && compte.getId() > 0) {
            // Error because personne is already saved
            if(comptaService.getCompteById(compte.getId()) != null) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Data exist already");
            }
        }
        compte.setPlan(plan);
        if(!compte.validateData()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect Data");
        }
        if(!comptaService.validateBusiness(compte)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect Data");
        }
        try {
            ComptaCompte compteSaved = comptaService.createCompte(compte);
            return compteSaved;
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    

    ////////// Compte /////////////
    @GetMapping("/compte")
    public Iterable<ComptaCompte> getComptes() {
        return comptaService.getComptes();
    }
  
    @GetMapping("/compte/{id}")
    public ComptaCompte getCompteById(@PathVariable int id) {
        return comptaService.getCompteById(id);
    }

    @PostMapping("/compte")
    public ComptaCompte addPlan(@Valid @RequestBody ComptaCompte compte, BindingResult results) {
        if(results.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, results.getAllErrors().get(0).getDefaultMessage());
        }
        if(compte.getId() != null && compte.getId() > 0) {
            // Error because personne is already saved
            if(comptaService.getCompteById(compte.getId()) != null) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Data exist already");
            }
        }
        if(!compte.validateData()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect Data");
        }
        if(!comptaService.validateBusiness(compte)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect Data");
        }
        try {
            ComptaCompte compteSaved = comptaService.createCompte(compte);
            return compteSaved;
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    
    @PutMapping("compte/{id}")
    public ComptaCompte savePlan(@PathVariable int id, @Valid @RequestBody ComptaCompte plan) {
        ComptaCompte oldCompte = comptaService.getCompteById(id);
        // TODO map plan to oldPlan
        return comptaService.updateCompte(oldCompte);
    }

    @DeleteMapping("compte/{id}")
    public ComptaCompte deleteCompte(@PathVariable int id) {
      // TODO check if exist + if can delete
      return comptaService.deleteCompte(id);
    }

    
    ////////// Groupe ////////////
    /// 
    ////////// Exception /////////
    /// @ExceptionHandler(ComptaNotFoundException.class)
    /// public ResponseEntity<String> handleComptaNotFound(ComptaNotFoundException ex) {
    ///   return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND)
    /// }
    /*
     * @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public Adventure addAdventure(@RequestBody Adventure adventure) {
    if(adventureRepository.findById(adventure.getId()).isPresent()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Data exist already");
    }
    if(adventure.validateNumPhotos() && adventure.validateDate()) {
      return adventureRepository.save(adventure);
    } else {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Bad data");
    }
  }
     */
}
