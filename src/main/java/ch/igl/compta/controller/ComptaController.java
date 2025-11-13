package ch.igl.compta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ch.igl.compta.model.ComptaEntity;
import ch.igl.compta.service.api.ComptaService;

@RestController
@RequestMapping("/api/v1/test/compta")
public class ComptaController {

    @Autowired
    private ComptaService comptaService;

    ///////// Lines /////////////

    @GetMapping("")
    public Iterable<ComptaEntity> getLines() {
        return comptaService.getComptaEntities();
    }

    @GetMapping("/byYear/{year}")
    public void getLinesByYear(@PathVariable int year) {
      throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Not yet finished");
    }

    ////////// Plan /////////////////


    ////////// Compte /////////////
    

    
    ////////// Groupe ////////////
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
