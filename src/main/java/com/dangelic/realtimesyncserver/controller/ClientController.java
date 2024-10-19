package com.dangelic.realtimesyncserver.controller;

import com.dangelic.realtimesyncserver.model.Client;
import com.dangelic.realtimesyncserver.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping("/connect")
    public Client connectClient(@RequestBody Client clientData) {
        System.out.println("POST /api/clients/connect - Client connected");
        return clientRepository.save(clientData);
    }

    @GetMapping("/")
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
}
