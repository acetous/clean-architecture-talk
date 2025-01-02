package de.makerhub.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Account, UUID> {

    @Query("SELECT account from Account account JOIN FETCH account.printedModels WHERE account.uuid = ?1")
    Account findByIdFetchPrintedModels(UUID uuid);
}
