package de.makerhub.domain;

import de.makerhub.domain.model.Collection;
import de.makerhub.domain.model.Model;
import de.makerhub.port.LoadCollectionPort;
import de.makerhub.port.LoadModelPort;
import de.makerhub.port.UpdateCollectionPort;
import de.makerhub.usecase.AddModelToCollectionUseCase;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
class CollectionService implements AddModelToCollectionUseCase {

    private final LoadCollectionPort loadCollectionPort;
    private final UpdateCollectionPort updateCollectionPort;
    private final LoadModelPort loadModelPort;

    CollectionService(LoadCollectionPort loadCollectionPort, UpdateCollectionPort updateCollectionPort, LoadModelPort loadModelPort) {
        this.loadCollectionPort = loadCollectionPort;
        this.updateCollectionPort = updateCollectionPort;
        this.loadModelPort = loadModelPort;
    }

    @Override
    public void addModelToCollection(UUID modelUuid, UUID collectionUuid) {
        Collection collection = loadCollectionPort.loadByUuid(collectionUuid);
        Model model = loadModelPort.loadByUuid(modelUuid);

        ArrayList<Model> modelList = new ArrayList<>(collection.models());
        modelList.add(model);

        Collection updatedCollection = new Collection(
                collection.uuid(),
                collection.name(),
                collection.account(),
                modelList
        );

        updateCollectionPort.save(updatedCollection);
    }
}
