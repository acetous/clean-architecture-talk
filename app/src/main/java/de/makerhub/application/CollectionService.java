package de.makerhub.application;

import de.makerhub.application.port.in.AddModelToCollectionUseCase;
import de.makerhub.application.port.out.LoadCollectionPort;
import de.makerhub.application.port.out.LoadModelPort;
import de.makerhub.application.port.out.SaveCollectionPort;
import de.makerhub.domain.Collection;
import de.makerhub.domain.Model;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
class CollectionService implements AddModelToCollectionUseCase {

    private final LoadCollectionPort loadCollectionPort;
    private final SaveCollectionPort updateCollectionPort;
    private final LoadModelPort loadModelPort;

    CollectionService(LoadCollectionPort loadCollectionPort, SaveCollectionPort updateCollectionPort, LoadModelPort loadModelPort) {
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

        updateCollectionPort.update(updatedCollection);
    }
}
