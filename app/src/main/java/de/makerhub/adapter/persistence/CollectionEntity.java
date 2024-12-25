package de.makerhub.adapter.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
class CollectionEntity {
    @Id
    private UUID uuid;
    private String name;
    @ManyToOne
    private AccountEntity account;
    @ManyToMany
    private List<ModelEntity> models;
}

