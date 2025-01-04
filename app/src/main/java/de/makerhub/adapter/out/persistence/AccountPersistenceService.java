package de.makerhub.adapter.out.persistence;

import de.makerhub.application.port.out.LoadAccountPort;
import de.makerhub.application.port.out.SaveAccountPort;
import de.makerhub.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class AccountPersistenceService implements LoadAccountPort, SaveAccountPort {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public Account getById(UUID accountUuid) {
        AccountEntity accountEntity = accountRepository.findByUuidFetchModels(accountUuid).orElseThrow();
        return accountMapper.toAccount(accountEntity);
    }

    @Override
    public void update(Account account) {
        accountRepository.save(accountMapper.toEntity(account));
    }

    @Override
    public void create(Account account) {
        update(account);
    }
}
