package de.makerhub.adapter.persistence;

import de.makerhub.domain.model.Model;
import de.makerhub.port.LoadModelPort;
import de.makerhub.port.UpdateModelPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class ModelPersistenceService implements LoadModelPort, UpdateModelPort {

    private final ModelRepository modelRepository;
    private final ModelMapper modelMapper;

    @Override
    public Model loadByUuid(UUID modelUuid) {
        ModelEntity entity = modelRepository.findById(modelUuid).orElseThrow(ResouceNotFoundException::new);
        return modelMapper.toModel(entity);
    }

    @Override
    public void updateModel(Model model) {
        modelRepository.save(modelMapper.toEntity(model));
    }
}
