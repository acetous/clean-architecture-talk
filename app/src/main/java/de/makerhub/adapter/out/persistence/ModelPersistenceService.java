package de.makerhub.adapter.out.persistence;

import de.makerhub.domain.Model;
import de.makerhub.application.port.out.LoadModelPort;
import de.makerhub.application.port.out.UpdateModelPort;
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
