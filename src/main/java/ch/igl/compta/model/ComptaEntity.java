package ch.igl.compta.model;

import org.springframework.data.annotation.Id;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;

@Data
@Entity
@Table(name = "comptaLines")
public class ComptaEntity {

    @Id
    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    private Double ammount;

    private String ownerStr;
    @ManyToOne
    @JoinColumn(name="personneId")
    private Personne owner;
}
