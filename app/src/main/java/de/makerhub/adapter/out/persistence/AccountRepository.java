package de.makerhub.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

    @Query("SELECT a FROM AccountEntity a JOIN FETCH a.printedModels WHERE a.uuid = ?1")
    Optional<AccountEntity> findByUuidFetchModels(UUID uuid);
}
