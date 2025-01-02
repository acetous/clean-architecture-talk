package de.makerhub.adapter.out.persistence;

import de.makerhub.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class AccountMapper {

    private final ModelMapper modelMapper;

    Account toAccount(AccountEntity entity) {
        return new Account(
                entity.getUuid(),
                entity.getUsername(),
                entity.getPrintedModels().stream().map(modelMapper::toModel).collect(Collectors.toSet())
        );
    }

    public AccountEntity toEntity(Account account) {
        AccountEntity entity = new AccountEntity();
        entity.setUuid(account.id());
        entity.setUsername(account.username());
        entity.setPrintedModels(account.printedModels().stream().map(modelMapper::toEntity).collect(Collectors.toSet()));
        return entity;
    }
}
