package com.dangelic.realtimesyncserver.service;

import com.dangelic.realtimesyncserver.dto.PositionDTO;
import com.dangelic.realtimesyncserver.model.Client;
import com.dangelic.realtimesyncserver.model.Position;
import com.dangelic.realtimesyncserver.repository.ClientRepository;
import com.dangelic.realtimesyncserver.repository.PositionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PositionService {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private ClientRepository clientRepository;

    public PositionDTO savePosition(Long clientId, PositionDTO positionDTO) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with id " + clientId));

        Position position = new Position();
        position.setXCoordinate(positionDTO.getXCoordinate());
        position.setYCoordinate(positionDTO.getYCoordinate());
        position.setClient(client);

        Position savedPosition = positionRepository.save(position);

        // Convert saved position to DTO before returning
        return new PositionDTO(savedPosition.getId(), savedPosition.getXCoordinate(), savedPosition.getYCoordinate(), clientId);
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
