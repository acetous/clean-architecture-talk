package de.makerhub.domain.model;

import java.util.List;
import java.util.UUID;

public record Collection(UUID uuid, String name, Account account, List<Model> models) {
}

