package de.makerhub.adapter.in.restapi;

import java.util.UUID;

public record ModelJson(UUID uuid, String name, String description, byte[] stlData, long printCount) {
}
