package de.makerhub;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Account {
    @Id
    private UUID uuid;
    private String username;

    @ManyToMany
    private List<Model> printedModels;
}
