package com.usermanagement.service;

import com.usermanagement.domain.entity.UserAction;
import com.usermanagement.domain.mapper.UserActionMapper;
import com.usermanagement.domain.repository.UserActionRepository;
import com.usermanagement.service.dto.UserActionDTO;
import org.springframework.stereotype.Service;

@Service
//@Transactional
public class UserActionService {

    private final UserActionRepository userActionRepository;

    private final UserActionMapper userActionMapper;

    public UserActionService(UserActionRepository userActionRepository, UserActionMapper userActionMapper) {
        this.userActionRepository = userActionRepository;
        this.userActionMapper = userActionMapper;
    }

    public UserActionDTO create(UserActionDTO userActionDTO) {
        UserAction createdUser = userActionRepository.save(userActionMapper.toEntity(userActionDTO));

        return userActionMapper.toDto(createdUser);
    }

    public void remove(Long id) {
        userActionRepository.delete(userActionRepository.findById(id).get());
    }

    public UserActionDTO update(UserActionDTO userActionDTO) {
        UserAction updatedUser = userActionRepository.save(userActionMapper.toEntity(userActionDTO));
        return userActionMapper.toDto(updatedUser);
    }
}
