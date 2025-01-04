package de.makerhub.domain;

import java.util.UUID;

public record Model(UUID id, String name, String description, byte[] stlData, long printCount) {
}
