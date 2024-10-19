package com.dangelic.realtimesyncserver.dto;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class ClientDTO {
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    private String status;
    private List<PositionDTO> positions; // maybe exclude it here?

    // Constructors
    public ClientDTO() {}

    public ClientDTO(Long id, String name, String status, List<PositionDTO> positions) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.positions = positions;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PositionDTO> getPositions() {
        return positions;
    }

    public void setPositions(List<PositionDTO> positions) {
        this.positions = positions;
    }
}
