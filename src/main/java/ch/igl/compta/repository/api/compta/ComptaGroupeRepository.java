package ch.igl.compta.repository.api.compta;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.igl.compta.model.ComptaCompteGroupe;

@Repository
public interface ComptaGroupeRepository extends CrudRepository<ComptaCompteGroupe, Long>{

}
