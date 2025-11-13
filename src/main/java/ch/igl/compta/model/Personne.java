package ch.igl.compta.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "Personne")
public class Personne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @CreationTimestamp(source = SourceType.DB)
    private LocalDateTime dateCreation;

    @UpdateTimestamp(source = SourceType.DB)
    private LocalDateTime dateModification;

    private String prenom;
    private String nom;
    @NotEmpty(message="L'email ne peut pas être vide")
    @Email(message="L'email n'est pas valide")
    private String mail;

    public boolean validateData() {
        return true;
    }
}
