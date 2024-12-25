package de.makerhub.adapter.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
class ModelEntity {
    @Id
    private UUID uuid;
    private String name;
    private String description;
    private byte[] stlData;
    private long printCount;
}
