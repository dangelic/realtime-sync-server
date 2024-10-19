package com.dangelic.realtimesyncserver.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PositionDTO {
    private Long id;
    // explicit definition as serialization seems to glitch here (without it confuses with "xcoordinate")
    @JsonProperty("xCoordinate")
    private double xCoordinate;
    @JsonProperty("yCoordinate")
    private double yCoordinate;
    private Long clientId;

    // Constructors
    public PositionDTO() {}

    public PositionDTO(Long id, double xCoordinate, double yCoordinate, Long clientId) {
        this.id = id;
        this.xCoordinate = xCoordinate; // Constructor should set this
        this.yCoordinate = yCoordinate;
        this.clientId = clientId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public double getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
