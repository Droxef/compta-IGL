package ch.igl.compta.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.Version;
import lombok.Data;

@Data
@Entity
@Table(name = "ComptaLines")
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")
public class ComptaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @CreationTimestamp(source = SourceType.DB)
    private LocalDateTime dateCreation;

    @UpdateTimestamp(source = SourceType.DB)
    private LocalDateTime dateModification;

    private LocalDateTime dateQuittance;

    private Double ammount;

    private String libele;

    private transient String ownerStr;

    @ManyToOne
    @JoinColumn(name="personneId")
    private Personne owner;

    @ManyToOne
    @JoinColumn(name="compteInId")
    private ComptaCompte compteIn;

    @Transient
    private transient String descriptionInTR;

    @ManyToOne
    @JoinColumn(name="compteOutId")
    private ComptaCompte compteOut;

    @Transient
    private transient String descriptionOutTR;

    @ManyToOne
    @JoinColumn(name="centreChargeId")
    private CentreCharge centreCharge;

}
