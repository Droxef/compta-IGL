package ch.igl.compta.repository.api.compta;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ch.igl.compta.model.ComptaPlan;


@Repository
public interface ComptaPlanRepository extends CrudRepository<ComptaPlan, Long> {

    @Query("select a from ComptaPlan a where a.startDate <= :year and a.endDate > :year")
    public Optional<ComptaPlan> findByYear(@Param("year") LocalDate year);
    public Optional<ComptaPlan> findByName(String name);
    public Iterable<ComptaPlan> findByIsOpenTrue();
    @Query("select a from ComptaPlan a where isOpen is true and a.endDate = (select MAX(b.endDate) from ComptaPlan b where isOpen is true)")
    public Optional<ComptaPlan> findLastIsOpen();

    public Optional<ComptaPlan> findByStartDateGreaterThanAndEndDateAfter(LocalDate yearStart, LocalDate yearEnd);
}
