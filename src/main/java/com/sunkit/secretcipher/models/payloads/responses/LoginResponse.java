package com.sunkit.secretcipher.models.payloads.responses;

import com.sunkit.secretcipher.models.dtos.UserDTO;
import com.sunkit.secretcipher.models.user.User;

public record LoginResponse(UserDTO user, JwtTokens tokens) {

    public static LoginResponse of(User user, JwtTokens tokens) {
        return new LoginResponse(UserDTO.of(user), tokens);
    }
}
