package com.Skill.Marketplace.SM.Services;

import com.Skill.Marketplace.SM.DTO.UserDTO.CreateUserDTO;
import com.Skill.Marketplace.SM.Entities.UserModel;
import com.Skill.Marketplace.SM.Entities.UserType;
import com.Skill.Marketplace.SM.Repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldCreateUserSuccessfully() {

        CreateUserDTO dto = new CreateUserDTO();
        dto.setUsername("manan");
        dto.setPassword("12345678");
        dto.setUserType(UserType.CONSUMER);

        when(passwordEncoder.encode(any()))
                .thenReturn("encodedPass");

        when(userRepo.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserModel user = userService.createNewUser(dto);

        assertEquals("manan", user.getUsername());
        verify(userRepo).save(any());
    }
}
