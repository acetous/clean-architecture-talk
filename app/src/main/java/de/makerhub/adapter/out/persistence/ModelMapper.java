package de.makerhub.adapter.out.persistence;

import de.makerhub.domain.Model;
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
        entity.setUuid(model.id());
        entity.setName(model.name());
        entity.setDescription(model.description());
        entity.setStlData(model.stlData());
        entity.setPrintCount(model.printCount());
        return entity;
    }
}
