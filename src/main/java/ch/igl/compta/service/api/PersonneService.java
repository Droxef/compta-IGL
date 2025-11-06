package ch.igl.compta.service.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.igl.compta.model.Personne;
import ch.igl.compta.repository.api.PersonneRepository;
import lombok.Data;

@Data
@Service
public class PersonneService {

    @Autowired
    private PersonneRepository personneRepository;

    public Iterable<Personne> getPersonnes() {
        return personneRepository.findAll();
    }

    public Personne getPersonneById(long id) {
        if(id <= 0) {
            return null;
        }
        Optional<Personne> personne = personneRepository.findById(id);
        if(personne.isPresent()) {
            return personne.get();
        } else {
            return null;
        }
    }

    public Personne getPersonneByMail(String mail) {
        if(mail == null || mail.isBlank()) {
            return null;
        }
        Optional<Personne> personne = personneRepository.findByMail(mail);
        if(personne.isPresent()) {
            return personne.get();
        } else {
            return null;
        }
    }

    public Personne createPersonne(Personne personne) {
        if(personne == null) {
            return null;
        }
        // test fields too
        return personneRepository.save(personne);
    }

    public Personne updatePersonne(Personne personne) {
        return personneRepository.save(personne);
    }

    public Personne deletePersonneById(Long id) {
        if(id <= 0) {
            return null;
        }
        Optional<Personne> personne = personneRepository.findById(id);
        if(personne.isPresent()) {
            personneRepository.delete(personne.get());
            return personne.get();
        } else {
            return null;
        }
    }

    public boolean validateBusiness(Personne personne) {
        return true;
    }


}
