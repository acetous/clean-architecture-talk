package de.makerhub.domain.model;

import java.util.UUID;

public record Model(UUID uuid, String name, String description, byte[] stlData, long printCount) {
}
