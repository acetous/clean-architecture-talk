package de.makerhub.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Collection {
    @Id
    private UUID uuid;
    private String name;
    @ManyToOne
    private Account account;
    @ManyToMany
    private List<Model> models;
}

