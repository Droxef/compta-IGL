package ch.igl.compta.repository.api.compta;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ch.igl.compta.model.ComptaEntity;

@Repository
public interface ComptaRepository extends CrudRepository<ComptaEntity, Long> {


    public Iterable<ComptaEntity> getLinesByYearPlan(@Param("year") LocalDate year);
}