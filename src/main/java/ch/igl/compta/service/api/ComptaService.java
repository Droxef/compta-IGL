package ch.igl.compta.service.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.igl.compta.model.ComptaEntity;
import ch.igl.compta.repository.api.ComptaRepository;
import lombok.Data;

@Data
@Service
public class ComptaService {

    @Autowired
    private ComptaRepository comptaRepository;

    public Optional<ComptaEntity> getComptaEntity(final Long id) {
        return comptaRepository.findById(id);
    }

    public Iterable<ComptaEntity> getComptaEntities() {
        return comptaRepository.findAll();
    }

    public void deleteComptaEntity(final Long id) {
        comptaRepository.deleteById(id);
    }

    public ComptaEntity saveComptaEntity(ComptaEntity comptaEntity) {
        ComptaEntity ce = comptaRepository.save(comptaEntity);
        return ce;
    }
}
