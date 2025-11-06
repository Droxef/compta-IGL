package ch.igl.compta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ch.igl.compta.model.ComptaEntity;
import ch.igl.compta.service.api.ComptaService;

@RestController
public class ComptaController {

    @Autowired
    private ComptaService comptaService;

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/compta")
    public Iterable<ComptaEntity> getLines() {
        return comptaService.getComptaEntities();
    }

    @GetMapping("/compta/byYear/{year}")
    public void getLinesByYear(@PathVariable int year) {
      throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Not yet finished");
    }

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
