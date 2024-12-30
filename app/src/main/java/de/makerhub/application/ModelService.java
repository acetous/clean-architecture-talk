package de.makerhub.application;

import de.makerhub.domain.Model;
import de.makerhub.application.port.out.LoadModelPort;
import de.makerhub.application.port.out.UpdateModelPort;
import de.makerhub.application.port.in.PrintModelUseCase;
import de.makerhub.application.port.in.ViewModelUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
class ModelService implements PrintModelUseCase, ViewModelUseCase {

    private final LoadModelPort loadModelPort;
    private final UpdateModelPort updateModelPort;

    ModelService(LoadModelPort loadModelPort, UpdateModelPort updateModelPort) {
        this.loadModelPort = loadModelPort;
        this.updateModelPort = updateModelPort;
    }

    @Override
    public byte[] printModel(UUID modelUuid) {
        Model model = loadModelPort.loadByUuid(modelUuid);

        Model updatedModel = new Model(
                model.uuid(),
                model.name(),
                model.description(),
                model.stlData(),
                model.printCount() + 1
        );

        updateModelPort.updateModel(updatedModel);

        return model.stlData();
    }

    @Override
    public Model loadModel(UUID modelUuid) {
        return loadModelPort.loadByUuid(modelUuid);
    }
}
