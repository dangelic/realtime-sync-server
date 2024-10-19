package com.dangelic.realtimesyncserver.service;

import com.dangelic.realtimesyncserver.model.Client;
import com.dangelic.realtimesyncserver.model.Position;
import com.dangelic.realtimesyncserver.repository.ClientRepository;
import com.dangelic.realtimesyncserver.repository.PositionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionService {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private ClientRepository clientRepository;

    public Position savePosition(Long clientId, Position position) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with id " + clientId));
        position.setClient(client);
        return positionRepository.save(position);
    }

    public Position getPositionForClient(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with id " + clientId));
        return positionRepository.findByClient(client)
                .stream()  // Convert List to Stream to get first element
                .findFirst()  // Return an Optional
                .orElseThrow(() -> new RuntimeException("No position found for client with id " + clientId));
    }

    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    public Position updatePosition(Long positionId, Position newPositionData) {
        Position position = positionRepository.findById(positionId)
                .orElseThrow(() -> new RuntimeException("Position not found with id " + positionId));

        position.setXCoordinate(newPositionData.getXCoordinate());
        position.setYCoordinate(newPositionData.getYCoordinate());

        return positionRepository.save(position);
    }

    public void deletePosition(Long positionId) {
        if (!positionRepository.existsById(positionId)) {
            throw new RuntimeException("Position not found with id " + positionId);
        }
        positionRepository.deleteById(positionId);
    }

    public List<Position> getAllPositionsForClient(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with id " + clientId));
        return positionRepository.findByClient(client);  // This is now a List
    }
}
