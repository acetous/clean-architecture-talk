package de.makerhub.application.port.in;

import java.util.UUID;

public interface PrintModelUseCase {

    byte[] printModel(UUID modelUuid);
}
