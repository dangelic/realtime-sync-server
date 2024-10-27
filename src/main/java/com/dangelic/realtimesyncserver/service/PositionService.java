package com.dangelic.realtimesyncserver.service;

import com.dangelic.realtimesyncserver.dto.PositionDTO;
import com.dangelic.realtimesyncserver.model.Client;
import com.dangelic.realtimesyncserver.model.Position;
import com.dangelic.realtimesyncserver.repository.ClientRepository; // Import this if not already present
import com.dangelic.realtimesyncserver.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PositionService {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private ClientRepository clientRepository; // Inject the client repository

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public PositionDTO savePosition(Long clientId, PositionDTO positionDTO) {
        // Fetch the Client entity based on the clientId
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + clientId));

        // Check if the client already has a position
        Position position = positionRepository.findByClient(client).orElse(null);

        if (position == null) {
            // If no position exists, create a new one
            position = new Position();
            position.setClient(client); // Set the Client object here
        }

        // Update the coordinates of the position
        position.setXCoordinate(positionDTO.getXCoordinate());
        position.setYCoordinate(positionDTO.getYCoordinate());

        // Save the updated position in the database
        Position savedPosition = positionRepository.save(position);

        // Create DTO for the saved position
        PositionDTO savedPositionDTO = new PositionDTO(
                savedPosition.getId(),
                savedPosition.getXCoordinate(),
                savedPosition.getYCoordinate(),
                savedPosition.getClient().getId() // Fetch the client ID from the saved position's client
        );

        // Broadcast the position update to all connected clients
        messagingTemplate.convertAndSend("/topic/positions", savedPositionDTO);

        return savedPositionDTO;
    }


    public List<PositionDTO> getAllPositionsForClient(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with id " + clientId));
        return positionRepository.findByClient(client)
                .stream()
                .map(position -> new PositionDTO(position.getId(), position.getXCoordinate(), position.getYCoordinate(), clientId))
                .collect(Collectors.toList());
    }

    public PositionDTO getPositionById(Long positionId) {
        Position position = positionRepository.findById(positionId)
                .orElseThrow(() -> new RuntimeException("Position not found with id " + positionId));
        return new PositionDTO(position.getId(), position.getXCoordinate(), position.getYCoordinate(), position.getClient().getId());
    }

    public void deletePosition(Long positionId) {
        if (!positionRepository.existsById(positionId)) {
            throw new RuntimeException("Position not found with id " + positionId);
        }
        positionRepository.deleteById(positionId);
    }
}
