package com.usermanagement.service;


import com.commonlib.config.LiquibaseConfig;
import com.usermanagement.domain.entity.User;
import com.usermanagement.domain.mapper.UserMapper;
import com.usermanagement.domain.repository.UserRepository;
import com.commonlib.util.exception.BadRequestException;
import com.commonlib.service.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebMergedContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
//@EnableAutoConfiguration(exclude= {DataSourceAutoConfiguration.class})
@ActiveProfiles("test")
//@WebMvcTest(controllers = UserResource.classFlywayAutoConfiguration)
public class UserServiceTest {

    @MockBean
    private UserRepository userRepositoryMock;

    @Autowired
    private UserMapper userMapper;

    // Injecting mocks into UserService
    @Autowired
    private UserService userService;

    UserDTO userDTO;
    @BeforeEach
    void setUp() {
        userDTO = UserDTO.builder().id(1L).name("Ahmad").build();
    }

    @Test
    public void testFindById() {
        // Given
        UserDTO notCachedUserDTO = UserDTO.builder().id(444L).name("Ahmad").build();
        User notCachedUser = userMapper.toEntity(notCachedUserDTO);

        when(userRepositoryMock.findById(notCachedUser.getId())).thenReturn(Optional.of(notCachedUser));
        // When
        UserDTO foundedUserDTO = userService.findById(notCachedUser.getId());

        // Then
        assertEquals(notCachedUserDTO, foundedUserDTO); // Assert that user deletion was successful
        verify(userRepositoryMock, times(1)).findById(notCachedUser.getId()); // Verify that deleteUser method was called once
    }

    @Test
    public void testFindById_cacheable() {
        // Given
        User user = userMapper.toEntity(userDTO);

        when(userRepositoryMock.findById(user.getId())).thenReturn(Optional.of(user));
        // When
        UserDTO foundedUserDTO = userService.findById(userDTO.getId());

        // Then
        assertEquals(userDTO, foundedUserDTO); // Assert that user deletion was successful
        verify(userRepositoryMock, times(0)).findById(user.getId()); // Verify that deleteUser method was called once
    }

    @Test
    public void testFindById_BadRequestException() throws Exception {
        // Given
        User badReqyestUser = userMapper.toEntity(UserDTO.builder().id(999L).name("BadRequestException").build());

        when(userRepositoryMock.findById(badReqyestUser.getId())).thenReturn(Optional.empty());

        // Then
        assertThrows(BadRequestException.class, () -> userService.findById(badReqyestUser.getId())); // Assert that user deletion was successful
        verify(userRepositoryMock, times(1)).findById(badReqyestUser.getId()); // Verify that deleteUser method was called once
    }

    @Test
    public void testCreateUser() {
        // Given
        User user = userMapper.toEntity(userDTO);

        when(userRepositoryMock.save(user)).thenReturn(user);
        // When
        UserDTO createdUserDTO = userService.create(userDTO);

       // Then
        assertEquals(userDTO, createdUserDTO); // Assert that user deletion was successful
        verify(userRepositoryMock, times(1)).save(user); // Verify that deleteUser method was called once
    }

    @Test
    public void testUpdateUser() {
        // Given
        User user = userMapper.toEntity(userDTO);

        when(userRepositoryMock.save(user)).thenReturn(user);
        // When
        UserDTO actualUserDTO = userService.update(userDTO);

       // Then
        UserDTO expectedUserDTO = UserDTO.builder().id(1L).name("Ahmad").build();
        assertEquals(expectedUserDTO, actualUserDTO); // Assert that user deletion was successful
        verify(userRepositoryMock, times(1)).save(user); // Verify that deleteUser method was called once
    }

    @Test
    public void testDeleteUser() {
        // Given
        User user = userMapper.toEntity(userDTO);

        // Mock behavior of userRepository.deleteUser(userId)
//        when(userRepositoryMock.findById(user.getId())).thenReturn(Optional.of(user));
        // When
        userService.remove(userDTO);
        //  Assert that user deletion was successful
        verify(userRepositoryMock, times(1)).delete(user); // Verify that deleteUser method was called once
    }
}
