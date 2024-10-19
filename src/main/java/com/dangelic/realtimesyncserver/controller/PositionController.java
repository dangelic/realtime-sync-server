package com.dangelic.realtimesyncserver.controller;

import com.dangelic.realtimesyncserver.model.Client;
import com.dangelic.realtimesyncserver.model.Position;
import com.dangelic.realtimesyncserver.repository.ClientRepository;
import com.dangelic.realtimesyncserver.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients/{clientId}/positions")
public class PositionController {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private ClientRepository clientRepository;

    // 1. Create or update client position
    @PostMapping
    public ResponseEntity<Position> createOrUpdatePosition(@PathVariable Long clientId, @RequestBody Position positionData) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (clientOptional.isPresent()) {
            Position position = new Position();
            position.setClient(clientOptional.get());
            position.setXCoordinate(positionData.getXCoordinate());
            position.setYCoordinate(positionData.getYCoordinate());
            Position savedPosition = positionRepository.save(position);
            return ResponseEntity.ok(savedPosition);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 2. Get positions by client ID
    @GetMapping
    public ResponseEntity<List<Position>> getPositionsByClientId(@PathVariable Long clientId) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (clientOptional.isPresent()) {
            List<Position> positions = positionRepository.findByClient(clientOptional.get());
            return ResponseEntity.ok(positions);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 3. Get position by position ID
    @GetMapping("/{positionId}")
    public ResponseEntity<Position> getPositionById(@PathVariable Long clientId, @PathVariable Long positionId) {
        Optional<Position> positionOptional = positionRepository.findById(positionId);
        return positionOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. Delete a position by ID
    @DeleteMapping("/{positionId}")
    public ResponseEntity<Void> deletePosition(@PathVariable Long clientId, @PathVariable Long positionId) {
        if (positionRepository.existsById(positionId)) {
            positionRepository.deleteById(positionId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
