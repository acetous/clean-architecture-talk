package de.makerhub.adapter.out.persistence;

import de.makerhub.application.port.out.LoadModelPort;
import de.makerhub.application.port.out.SaveModelPort;
import de.makerhub.domain.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class ModelPersistenceService implements LoadModelPort, SaveModelPort {

    private final ModelRepository modelRepository;
    private final ModelMapper modelMapper;

    @Override
    public Model loadByUuid(UUID modelUuid) {
        ModelEntity entity = modelRepository.findById(modelUuid).orElseThrow(ResouceNotFoundException::new);
        return modelMapper.toModel(entity);
    }

    @Override
    public void create(Model model) {
        update(model);
    }

    @Override
    public void update(Model model) {
        modelRepository.save(modelMapper.toEntity(model));
    }
}
