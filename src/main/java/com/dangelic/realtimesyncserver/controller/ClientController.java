package com.dangelic.realtimesyncserver.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    // Connect a new client
    @PostMapping("/connect")
    public String connectClient(@RequestBody String clientData) {
        System.out.println("POST /api/clients/connect - Client connected");
        return "{\"message\": \"Client connected\"}";
    }

    // Disconnect an existing client by clientId
    @PostMapping("/disconnect/{clientId}")
    public String disconnectClient(@PathVariable String clientId) {
        System.out.println("POST /api/clients/disconnect/" + clientId + " - Client disconnected");
        return "{\"message\": \"Client disconnected\"}";
    }

    // Get the status of a specific client by clientId
    @GetMapping("/{clientId}/status")
    public String getClientStatus(@PathVariable String clientId) {
        System.out.println("GET /api/clients/" + clientId + "/status - Client status retrieved");
        return "{\"message\": \"Client status retrieved\"}";
    }
}
