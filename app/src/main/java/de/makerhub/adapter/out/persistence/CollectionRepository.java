package de.makerhub.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface CollectionRepository extends JpaRepository<CollectionEntity, UUID> {
}
