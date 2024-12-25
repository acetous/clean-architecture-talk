package de.makerhub.port;

import de.makerhub.domain.model.Collection;

import java.util.UUID;

public interface LoadCollectionPort {
    Collection loadByUuid(UUID collectionUuid);
}
