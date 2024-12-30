package de.makerhub.application.port.in;

import java.util.UUID;

public interface AddModelToCollectionUseCase {
    void addModelToCollection(UUID modelUuid, UUID collectionUuid);
}
