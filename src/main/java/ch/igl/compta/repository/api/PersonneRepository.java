package ch.igl.compta.repository.api;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.igl.compta.model.Personne;

@Repository
public interface PersonneRepository extends CrudRepository<Personne, Long> {
    public List<Personne> findByNom(String nom);
    public List<Personne> findByPrenom(String prenom);
    public Optional<Personne> findByMail(String mail);
    public List<Personne> findByNomAndPrenom(String nom, String prenom);
}
