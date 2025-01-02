package de.makerhub.application.port.out;

import de.makerhub.domain.Account;

import java.util.UUID;

public interface LoadAccountPort {
    Account getById(UUID accountUuid);
}
