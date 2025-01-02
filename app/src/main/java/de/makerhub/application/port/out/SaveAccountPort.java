package de.makerhub.application.port.out;

import de.makerhub.domain.Account;

public interface SaveAccountPort {
    void update(Account account);

    void create(Account account);
}
