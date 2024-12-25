package de.makerhub.adapter.restapi;

import java.util.UUID;

public record ModelJson(UUID uuid, String name, String description, long printCount) {
}
