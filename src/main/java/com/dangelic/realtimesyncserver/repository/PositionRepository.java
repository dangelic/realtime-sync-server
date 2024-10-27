package com.dangelic.realtimesyncserver.repository;

import com.dangelic.realtimesyncserver.model.Client;
import com.dangelic.realtimesyncserver.model.Position;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    Optional<Position> findByClient(Client client);
}
