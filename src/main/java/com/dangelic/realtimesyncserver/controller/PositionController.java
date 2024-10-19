package com.dangelic.realtimesyncserver.controller;

import com.dangelic.realtimesyncserver.dto.PositionDTO;
import com.dangelic.realtimesyncserver.model.Client;
import com.dangelic.realtimesyncserver.model.Position;
import com.dangelic.realtimesyncserver.repository.ClientRepository;
import com.dangelic.realtimesyncserver.repository.PositionRepository;
import com.dangelic.realtimesyncserver.service.PositionService; // Import the service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients/{clientId}/positions")
public class PositionController {

    @Autowired
    private PositionService positionService;

    // 1. Create or update client position
    @PostMapping
    public ResponseEntity<PositionDTO> createOrUpdatePosition(@PathVariable Long clientId, @RequestBody PositionDTO positionDTO) {
        try {
            PositionDTO savedPositionDTO = positionService.savePosition(clientId, positionDTO);
            return ResponseEntity.ok(savedPositionDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 2. Get positions by client ID
    @GetMapping
    public ResponseEntity<List<PositionDTO>> getPositionsByClientId(@PathVariable Long clientId) {
        List<PositionDTO> positions = positionService.getAllPositionsForClient(clientId);
        return ResponseEntity.ok(positions);
    }

    // 3. Get position by position ID
    @GetMapping("/{positionId}")
    public ResponseEntity<PositionDTO> getPositionById(@PathVariable Long clientId, @PathVariable Long positionId) {
        PositionDTO position = positionService.getPositionById(positionId);
        return ResponseEntity.ok(position);
    }

    // 4. Delete a position by ID
    @DeleteMapping("/{positionId}")
    public ResponseEntity<Void> deletePosition(@PathVariable Long clientId, @PathVariable Long positionId) {
        positionService.deletePosition(positionId);
        return ResponseEntity.noContent().build();
    }
}
