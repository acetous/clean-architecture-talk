package de.makerhub.adapter.out.persistence;

import de.makerhub.application.port.out.LoadCollectionPort;
import de.makerhub.application.port.out.SaveCollectionPort;
import de.makerhub.domain.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CollectionPersistenceService implements LoadCollectionPort, SaveCollectionPort {

    private final CollectionRepository collectionRepository;
    private final CollectionMapper collectionMapper;

    @Override
    public Collection loadByUuid(UUID collectionUuid) {
        CollectionEntity collectionEntity = collectionRepository.findById(collectionUuid).orElseThrow(ResouceNotFoundException::new);
        return collectionMapper.toCollection(collectionEntity);
    }

    @Override
    public void create(Collection collection) {
        update(collection);
    }

    @Override
    public void update(Collection collection) {
        collectionRepository.save(collectionMapper.toEntity(collection));
    }
}
