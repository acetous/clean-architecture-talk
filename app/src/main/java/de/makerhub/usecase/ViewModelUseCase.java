package de.makerhub.usecase;

import de.makerhub.domain.model.Model;

import java.util.UUID;

public interface ViewModelUseCase {
    Model loadModel(UUID modelUuid);
}
