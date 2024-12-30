package de.makerhub.adapter.in.restapi;

import java.util.UUID;

public record ModelJson(UUID uuid, String name, String description, long printCount) {
}
