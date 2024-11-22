package com.company.jpa.dto;


import java.io.Serializable;


public record LoginResponse(String accessToken, String username) implements Serializable {
}
