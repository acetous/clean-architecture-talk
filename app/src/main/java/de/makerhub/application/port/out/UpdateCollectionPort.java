package de.makerhub.application.port.out;

import de.makerhub.domain.Collection;

public interface UpdateCollectionPort {
    void save(Collection collection);
}
