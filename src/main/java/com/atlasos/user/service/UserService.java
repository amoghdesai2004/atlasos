package com.atlasos.user.service;

import com.atlasos.user.dto.CreateUserRequest;
import com.atlasos.user.dto.UserResponse;
import com.atlasos.user.entity.User;
import com.atlasos.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.atlasos.common.exception.DuplicateResourceException;
import com.atlasos.common.exception.ResourceNotFoundException;
import java.util.UUID;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public UserResponse createUser(CreateUserRequest request) {

        if (userRepository.existsByEmail(request.email())) {
        throw new DuplicateResourceException(
                "A user with this email already exists"
        );
    }

        User user = new User();

        user.setName(request.name());
        user.setEmail(request.email());
        user.setRole(request.role());

        User savedUser = userRepository.save(user);

        return toResponse(savedUser);
    }

    private UserResponse toResponse(User user) {

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
    public UserResponse getUserById(UUID id) {

    User user = userRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "User not found with id: " + id
                    )
            );

    return toResponse(user);
}
    
}