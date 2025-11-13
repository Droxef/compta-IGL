package ch.igl.compta.repository.api.compta;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ch.igl.compta.model.ComptaEntity;

@Repository
public interface ComptaRepository extends CrudRepository<ComptaEntity, Long> {

    @Query(value="select l from ComptaPlan p join p.comptes c join c.linesIn l where p.startDate <= :year and p.endDate > :year")
    public Iterable<ComptaEntity> getLinesByYearPlan(@Param("year") LocalDate year);
}