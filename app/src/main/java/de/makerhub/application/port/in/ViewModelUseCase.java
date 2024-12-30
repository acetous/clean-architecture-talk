package de.makerhub.application.port.in;

import de.makerhub.domain.Model;

import java.util.UUID;

public interface ViewModelUseCase {
    Model loadModel(UUID modelUuid);
}
