package de.makerhub.adapter.in.web;

import java.util.UUID;

record ModelJson(UUID id, String name, String description, byte[] stlData, long printCount) {
}
