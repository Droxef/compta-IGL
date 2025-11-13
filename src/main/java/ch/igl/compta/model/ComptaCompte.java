package ch.igl.compta.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;

@Data
@Entity
@Table
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")
public class ComptaCompte {

    public enum ComptaType {
        BILAN,
        COMPTE_FUNC
    }

    public enum ComptaCategory {
        ACTIF("Actifs"),
        PASSIF("Passifs"),
        PRODUIT("Produits"),
        CHARGE("Charges");

        private final String text;

        private ComptaCategory(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @CreationTimestamp(source = SourceType.DB)
    private LocalDateTime dateCreation;

    @UpdateTimestamp(source = SourceType.DB)
    private LocalDateTime dateModification;

    private Integer numero;

    private ComptaType type;

    private ComptaCategory category;

    private String description;

    private Double startSolde;

    private Double budgetPlan;

    @ManyToOne
    @JoinColumn(name="planId")
    //@JsonBackReference
    private ComptaPlan plan;

    //@JsonBackReference
    @ManyToOne
    @JoinColumn(name="groupId")
    private ComptaCompteGroupe groupe;

    @JsonManagedReference
    @OneToMany(targetEntity=ComptaEntity.class, cascade=CascadeType.ALL, mappedBy="compteOut")
    private List<ComptaEntity> linesOut;

    @JsonManagedReference
    @OneToMany(targetEntity=ComptaEntity.class, cascade=CascadeType.ALL, mappedBy="compteIn")
    private List<ComptaEntity> linesIn;

    public boolean validateData() {
        return true;
    }
}
