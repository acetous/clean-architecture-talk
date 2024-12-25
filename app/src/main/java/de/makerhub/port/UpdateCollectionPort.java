package de.makerhub.port;

import de.makerhub.domain.model.Collection;

public interface UpdateCollectionPort {
    void save(Collection collection);
}
