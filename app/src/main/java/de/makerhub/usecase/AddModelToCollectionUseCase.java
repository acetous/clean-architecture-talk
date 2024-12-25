package de.makerhub.usecase;

import java.util.UUID;

public interface AddModelToCollectionUseCase {
    void addModelToCollection(UUID modelUuid, UUID collectionUuid);
}
