package de.makerhub.adapter.out.persistence;

import de.makerhub.domain.Collection;
import de.makerhub.application.port.out.LoadCollectionPort;
import de.makerhub.application.port.out.UpdateCollectionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CollectionPersistenceService implements LoadCollectionPort, UpdateCollectionPort {

    private final CollectionRepository collectionRepository;
    private final CollectionMapper collectionMapper;

    @Override
    public Collection loadByUuid(UUID collectionUuid) {
        CollectionEntity collectionEntity = collectionRepository.findById(collectionUuid).orElseThrow(ResouceNotFoundException::new);
        return collectionMapper.toCollection(collectionEntity);
    }

    @Override
    public void save(Collection collection) {
        collectionRepository.save(collectionMapper.toEntity(collection));
    }
}
