package com.usermanagement.domain.mapper;

import com.usermanagement.domain.entity.User;
import com.commonlib.domain.mapper.EntityMapper;
import com.commonlib.service.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserMapper extends ModelMapper implements EntityMapper<UserDTO, User> {

    @Override
    public User toEntity(UserDTO userDTO) {
        return  map(userDTO, User.class);
    }

    @Override
    public UserDTO toDto(User user) {
        return map(user, UserDTO.class);
    }
}
