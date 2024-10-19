package com.dangelic.realtimesyncserver.controller;

import com.dangelic.realtimesyncserver.dto.ClientDTO;
import com.dangelic.realtimesyncserver.dto.PositionDTO;
import com.dangelic.realtimesyncserver.model.Client;
import com.dangelic.realtimesyncserver.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping("/connect")
    public ClientDTO connectClient(@RequestBody ClientDTO clientData) {
        Client client = new Client(clientData.getName(), clientData.getStatus());
        Client savedClient = clientRepository.save(client);
        return new ClientDTO(savedClient.getId(), savedClient.getName(), savedClient.getStatus(), null);
    }

    @GetMapping("/")
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(client -> {
                    List<PositionDTO> positions = client.getPositions().stream()
                            .map(position -> new PositionDTO(position.getId(), position.getXCoordinate(), position.getYCoordinate(), client.getId()))
                            .collect(Collectors.toList());
                    return new ClientDTO(client.getId(), client.getName(), client.getStatus(), positions);
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        return clientOptional.map(client -> {
                    List<PositionDTO> positions = client.getPositions().stream()
                            .map(position -> new PositionDTO(position.getId(), position.getXCoordinate(), position.getYCoordinate(), client.getId()))
                            .collect(Collectors.toList());
                    return new ClientDTO(client.getId(), client.getName(), client.getStatus(), positions);
                })
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody ClientDTO clientData) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            client.setName(clientData.getName());
            client.setStatus(clientData.getStatus());
            Client updatedClient = clientRepository.save(client);
            return ResponseEntity.ok(new ClientDTO(updatedClient.getId(), updatedClient.getName(), updatedClient.getStatus(), null));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
