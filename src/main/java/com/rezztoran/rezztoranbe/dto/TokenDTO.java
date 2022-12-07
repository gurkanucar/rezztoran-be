package com.rezztoran.rezztoranbe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {
    private String accessToken;
    private UserDTO user;
}
