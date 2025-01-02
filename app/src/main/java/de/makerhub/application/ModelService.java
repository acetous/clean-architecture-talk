package de.makerhub.application;

import de.makerhub.application.port.in.PrintModelUseCase;
import de.makerhub.application.port.in.ViewModelUseCase;
import de.makerhub.application.port.out.CurrentUserPort;
import de.makerhub.application.port.out.LoadModelPort;
import de.makerhub.application.port.out.SaveAccountPort;
import de.makerhub.application.port.out.SaveModelPort;
import de.makerhub.domain.Account;
import de.makerhub.domain.Model;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
class ModelService implements PrintModelUseCase, ViewModelUseCase {

    private final LoadModelPort loadModelPort;
    private final SaveModelPort updateModelPort;
    private final CurrentUserPort currentUserPort;
    private final SaveAccountPort saveAccountPort;

    ModelService(LoadModelPort loadModelPort, SaveModelPort updateModelPort, CurrentUserPort currentUserPort, SaveAccountPort saveAccountPort) {
        this.loadModelPort = loadModelPort;
        this.updateModelPort = updateModelPort;
        this.currentUserPort = currentUserPort;
        this.saveAccountPort = saveAccountPort;
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

        updateModelPort.update(updatedModel);

        Account currentUser = currentUserPort.getCurrentUser();
        Set<Model> printedModels = currentUser.printedModels();
        printedModels.add(updatedModel);
        saveAccountPort.update(new Account(
                currentUser.id(),
                currentUser.username(),
                printedModels
        ));

        return model.stlData();
    }

    @Override
    public Model loadModel(UUID modelUuid) {
        return loadModelPort.loadByUuid(modelUuid);
    }
}
