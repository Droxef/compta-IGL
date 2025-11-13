package ch.igl.compta.model;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="planId")
    private ComptaPlan plan;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="parentId")
    private ComptaCompteGroupe parent;

    @JsonManagedReference
    @OneToMany(targetEntity=ComptaCompteGroupe.class, cascade=CascadeType.ALL, mappedBy="parent")
    private Set<ComptaCompteGroupe> children;

    //@JsonManagedReference
    @OneToMany(targetEntity=ComptaCompte.class, cascade=CascadeType.ALL, mappedBy="groupe")
    private Set<ComptaCompte> comptes;
}
