package com.atlasos.user.dto;

import com.atlasos.user.entity.UserRole;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String email,
        UserRole role,
        Instant createdAt,
        Instant updatedAt
) {
}