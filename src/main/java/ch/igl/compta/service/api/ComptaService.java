package ch.igl.compta.service.api;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.igl.compta.model.ComptaCompte;
import ch.igl.compta.model.ComptaCompteGroupe;
import ch.igl.compta.model.ComptaEntity;
import ch.igl.compta.model.ComptaPlan;
import ch.igl.compta.repository.api.compta.ComptaCompteRepository;
import ch.igl.compta.repository.api.compta.ComptaGroupeRepository;
import ch.igl.compta.repository.api.compta.ComptaPlanRepository;
import ch.igl.compta.repository.api.compta.ComptaRepository;
import lombok.Data;

@Data
@Service
public class ComptaService {

    @Autowired
    private ComptaRepository comptaRepository;

    @Autowired
    private ComptaCompteRepository comptaCompteRepository;

    @Autowired
    private ComptaGroupeRepository comptaGroupeRepository;

    @Autowired
    private ComptaPlanRepository comptaPlanRepository;

    public ComptaPlan getPlanById(final long id) {
        if(id <= 0) {
            return null;
        }
        Optional<ComptaPlan> plan =  comptaPlanRepository.findById(id);
        if(plan.isPresent()) {
            return plan.get();
        } else {
            return null;
        }
    }

    public ComptaCompteGroupe getGroupeById(final long id) {
        if(id <= 0) {
            return null;
        }
        Optional<ComptaCompteGroupe> groupe = comptaGroupeRepository.findById(id);
        if(groupe.isPresent()) {
            return groupe.get();
        } else {
            return null;
        }
    }

    public ComptaCompte getCompteById(final long id) {
        if(id <= 0) {
            return null;
        }
        Optional<ComptaCompte> compte = comptaCompteRepository.findById(id);
        if(compte.isPresent()) {
            return compte.get();
        } else {
            return null;
        }
    }

    public ComptaEntity getLineById(final Long id) {
        if(id <= 0) {
            return null;
        }
        Optional<ComptaEntity> line = comptaRepository.findById(id);
        if(line.isPresent()) {
            return line.get();
        } else {
            return null;
        }
    }

    public Iterable<ComptaPlan> getPlans() {
        return comptaPlanRepository.findAll();
    }

    public Iterable<ComptaCompteGroupe> getGroupes() {
        return comptaGroupeRepository.findAll();
    }

    public Iterable<ComptaCompte> getComptes() {
        return comptaCompteRepository.findAll();
    }

    public Iterable<ComptaEntity> getLines() {
        return comptaRepository.findAll();
    }

    public ComptaPlan getPlanByYear(final int year) {
        LocalDate yearDate = LocalDate.of(year, 1, 1);
        Optional<ComptaPlan> plan = comptaPlanRepository.findByYear(yearDate);
        if(plan.isPresent()) {
            return plan.get();
        } else {
            return null;
        }
    }

    public Iterable<ComptaCompte> getCompteByYear(final int year) {
        LocalDate yearDate = LocalDate.of(year, 1, 1);
        Optional<ComptaPlan> plan = comptaPlanRepository.findByYear(yearDate);
        if(plan.isPresent()) {
            return plan.get().getComptes();
        } else {
            return null;
        }
    }

    public Iterable<ComptaEntity> getLinesByYear(final int year) {
        LocalDate yearDate = LocalDate.of(year, 1, 1);
        return comptaRepository.getLinesByYearPlan(yearDate);
    }



    public ComptaPlan createPlan(ComptaPlan plan) {
        if(plan == null) {
            return null;
        }
        return comptaPlanRepository.save(plan);
    }

    public ComptaCompteGroupe createGroupe(ComptaCompteGroupe groupe) {
        if(groupe == null) {
            return null;
        }
        return comptaGroupeRepository.save(groupe);
    }

    public ComptaCompte createCompte(ComptaCompte compte) {
        if(compte == null) {
            return null;
        }
        return comptaCompteRepository.save(compte);
    }

    public ComptaEntity createLine(ComptaEntity line) {
        if(line == null) {
            return null;
        }
        return comptaRepository.save(line);
    }

    public ComptaPlan updatePlan(ComptaPlan plan) {
        return comptaPlanRepository.save(plan);
    }

    public ComptaCompteGroupe updateGroupe(ComptaCompteGroupe groupe) {
        return comptaGroupeRepository.save(groupe);
    }

    public ComptaCompte updateCompte(ComptaCompte compte) {
        return comptaCompteRepository.save(compte);
    }

    public ComptaEntity updateLine(ComptaEntity comptaEntity) {
        ComptaEntity ce = comptaRepository.save(comptaEntity);
        return ce;
    }

    public void deletePlan(final Long id) {
        comptaPlanRepository.deleteById(id);
    }

    public void deleteGroupe(final Long id) {
        comptaGroupeRepository.deleteById(id);
    }

    public void deleteCompte(final Long id) {
        comptaCompteRepository.deleteById(id);
    }

    public void deleteLine(final Long id) {
        comptaRepository.deleteById(id);
    }

    public boolean validateBusiness(ComptaPlan plan) {
        return true;
    }

    public boolean validateBusiness(ComptaCompteGroupe groupe) {
        return true;
    }

    public boolean validateBusiness(ComptaCompte compte) {
        return true;
    }

    public boolean validateBusiness(ComptaEntity line) {
        return true;
    }
}
