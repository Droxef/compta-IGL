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
import lombok.Data;

@Data
@Entity
@Table
public class CentreCharge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @CreationTimestamp(source = SourceType.DB)
    private LocalDateTime dateCreation;

    @UpdateTimestamp(source = SourceType.DB)
    private LocalDateTime dateModification;

    private String description;

    private Integer order;

    private Integer axis;
}
