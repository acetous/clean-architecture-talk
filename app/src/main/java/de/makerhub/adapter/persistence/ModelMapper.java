package de.makerhub.adapter.persistence;

import de.makerhub.domain.model.Model;
import org.springframework.stereotype.Service;

@Service
class ModelMapper {

    Model toModel(ModelEntity entity) {
        return new Model(
                entity.getUuid(),
                entity.getName(),
                entity.getDescription(),
                entity.getStlData(),
                entity.getPrintCount()
        );
    }

    public ModelEntity toEntity(Model model) {
        ModelEntity entity = new ModelEntity();
        entity.setUuid(model.uuid());
        entity.setName(model.name());
        entity.setDescription(model.description());
        entity.setStlData(model.stlData());
        entity.setPrintCount(model.printCount());
        return entity;
    }
}
