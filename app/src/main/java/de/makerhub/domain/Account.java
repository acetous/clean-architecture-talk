package de.makerhub.domain;

import java.util.Set;
import java.util.UUID;

public record Account(UUID id, String username, Set<Model> printedModels) {
}
