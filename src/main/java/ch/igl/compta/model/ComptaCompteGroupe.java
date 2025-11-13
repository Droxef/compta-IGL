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
public class ComptaCompteGroupe {

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

    private String description;

    @ManyToOne
    @JoinColumn(name="planId")
    private ComptaPlan plan;

    @ManyToOne
    @JoinColumn(name="parentId")
    private ComptaCompteGroupe parent;

    @OneToMany(targetEntity=ComptaCompteGroupe.class, cascade=CascadeType.ALL, mappedBy="parent")
    private Set<ComptaCompteGroupe> children;

    @OneToMany(targetEntity=ComptaCompte.class, cascade=CascadeType.ALL, mappedBy="groupe")
    private Set<ComptaCompte> comptes;
}
