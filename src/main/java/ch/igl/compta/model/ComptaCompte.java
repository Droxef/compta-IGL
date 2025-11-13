package ch.igl.compta.model;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

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
    private ComptaPlan plan;

    @ManyToOne
    @JoinColumn(name="groupId")
    private ComptaCompteGroupe groupe;

    @OneToMany(targetEntity=ComptaEntity.class, cascade=CascadeType.ALL, mappedBy="compteOut")
    private Set<ComptaEntity> linesOut;

    @OneToMany(targetEntity=ComptaEntity.class, cascade=CascadeType.ALL, mappedBy="compteIn")
    private Set<ComptaEntity> linesIn;
}
