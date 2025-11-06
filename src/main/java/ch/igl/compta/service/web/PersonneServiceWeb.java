package ch.igl.compta.service.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.igl.compta.model.Personne;
import ch.igl.compta.repository.web.PersonneProxy;
import lombok.Data;

@Data
@Service
public class PersonneServiceWeb {

    // TODO replace Personne by PersonneDTO
    @Autowired
    private PersonneProxy personneProxy;

    public Personne getPersonne(final int id) {
        return personneProxy.getPersonne(id);
    }

    public Iterable<Personne> getAllPersonnes() {
        return personneProxy.getAllPersonnes();
    }

    public Personne savePersonne(Personne p) {
        Personne personne;

        if(p.getId() == null) {
            personne = personneProxy.createPersonne(p);
        } else {
            personne = personneProxy.updatePersonne(p);
        }

        return personne;
    }

    public Personne deletePersonne(final int id) {
        return personneProxy.deletePersonne(id);
    }
}
