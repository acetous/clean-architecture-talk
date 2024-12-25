package de.makerhub.port;

import de.makerhub.domain.model.Model;

import java.util.UUID;

public interface LoadModelPort {
    Model loadByUuid(UUID modelUuid);
}
