package de.makerhub.adapter.out.persistence;

import de.makerhub.domain.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CollectionMapper {

    private final AccountMapper accountMapper;
    private final ModelMapper modelMapper;

    public Collection toCollection(CollectionEntity entity) {
        return new Collection(
                entity.getUuid(),
                entity.getName(),
                accountMapper.toAccount(entity.getAccount()),
                entity.getModels().stream().map(modelMapper::toModel).toList()
        );
    }

    public CollectionEntity toEntity(Collection collection) {
        CollectionEntity entity = new CollectionEntity();
        entity.setUuid(collection.id());
        entity.setName(collection.name());
        entity.setAccount(accountMapper.toEntity(collection.account()));
        entity.setModels(collection.models().stream().map(modelMapper::toEntity).toList());
        return entity;
    }
}
