package de.makerhub.application.port.out;

import de.makerhub.domain.Collection;

import java.util.UUID;

public interface LoadCollectionPort {
    Collection loadByUuid(UUID collectionUuid);
}
