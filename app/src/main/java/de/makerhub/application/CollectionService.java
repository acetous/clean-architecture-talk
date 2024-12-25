package de.makerhub.application;

import de.makerhub.persistence.Collection;
import de.makerhub.persistence.CollectionRepository;
import de.makerhub.persistence.Model;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CollectionService {

    private final ModelService modelService;
    private final CollectionRepository collectionRepository;

    public void addModelToCollection(UUID modelUuid, UUID collectionUuid) {
        Model model = modelService.getModel(modelUuid);
        Collection collection = getCollection(collectionUuid);

        collection.getModels().add(model);
    }

    public Collection getCollection(UUID collectionUuid) {
        return collectionRepository.findById(collectionUuid).orElseThrow(ResouceNotFoundException::new);
    }
}
