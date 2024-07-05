package com.usermanagement.service;

import com.commonlib.annotation.EntityListener;
import com.usermanagement.domain.entity.User;
import com.usermanagement.domain.mapper.UserMapper;
import com.usermanagement.domain.repository.UserRepository;
import com.usermanagement.util.action.AuditingUserListener;
import com.commonlib.util.exception.BadRequestException;
import com.commonlib.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
//@Transactional
@EntityListener(listenerClass = AuditingUserListener.class,
        createMethods = {"create"},
        updateMethods = {"update"},
        deleteMethods = {"remove"})
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Cacheable(value = "users", key = "#id")
    public UserDTO findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new BadRequestException("User not found");
        }
        return userMapper.toDto(user.get());
    }

//    @Cacheable(value = "users")
    public Set<UserDTO> getAll() {
        return userRepository.findAll().stream().map(user -> userMapper.toDto(user)).
                sorted(Comparator.comparing(UserDTO::getId)).
                collect(Collectors.toSet());
    }

//    @CachePut(value = "users", key = "userDTO.id")
//    @CacheEvict(value = "users", allEntries = true)
//    @CachePut(value = "users", key = "#userDTO.name")
    @CacheEvict(value = "users", allEntries = true)
    public UserDTO create(UserDTO userDTO) {
        User createdUser = userRepository.save(userMapper.toEntity(userDTO));
        System.out.println(createdUser.getName());

        return userMapper.toDto(createdUser);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void remove(UserDTO userDTO) {
        userRepository.delete(userMapper.toEntity(userDTO));
    }

    @CachePut(value = "users", key = "#userDTO.id")
//    @CacheEvict(value = "users", allEntries = true)
    public UserDTO update(UserDTO userDTO) {

        User updatedUser = userRepository.save(userMapper.toEntity(userDTO));
        System.out.println(updatedUser.getName());

        return userMapper.toDto(updatedUser);
    }
}
