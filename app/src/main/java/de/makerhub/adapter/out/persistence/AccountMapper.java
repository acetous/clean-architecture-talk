package de.makerhub.adapter.out.persistence;

import de.makerhub.domain.Account;
import org.springframework.stereotype.Service;

@Service
class AccountMapper {

    Account toAccount(AccountEntity entity) {
        return new Account(
                entity.getUuid(),
                entity.getUsername()
        );
    }

    public AccountEntity toEntity(Account account) {
        AccountEntity entity = new AccountEntity();
        entity.setUuid(account.id());
        entity.setUsername(account.username());
        return entity;
    }
}
