package de.makerhub.application.port.out;

import de.makerhub.domain.Collection;

public interface SaveCollectionPort {
    void create(Collection collection);
    void update(Collection collection);
}
