package com.dangelic.realtimesyncserver.controller;

import com.dangelic.realtimesyncserver.dto.PositionDTO;
import com.dangelic.realtimesyncserver.exception.ClientRequestValidationException;
import com.dangelic.realtimesyncserver.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketPositionController {

    @Autowired
    private PositionService positionService;

    @MessageMapping("/positions") // Endpoint for WebSocket messages
    @SendTo("/topic/positions")   // Broadcast to subscribers
    public PositionDTO handlePositionUpdate(PositionDTO positionDTO) {
        try {

            System.out.println("HI");
            // Here you need to get the clientId from the positionDTO if needed
            Long clientId = positionDTO.getClientId();
            return positionService.savePosition(clientId, positionDTO); // Save position and broadcast
        } catch (RuntimeException e) {
            throw new ClientRequestValidationException("Invalid position data provided: " + e.getMessage());
        }
    }
}
