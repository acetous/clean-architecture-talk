package de.makerhub.application.port.out;

import de.makerhub.domain.Model;

import java.util.UUID;

public interface LoadModelPort {
    Model loadByUuid(UUID modelUuid);
}
