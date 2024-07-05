package com.usermanagement.domain.mapper;

import com.usermanagement.domain.entity.UserAction;
import com.commonlib.domain.mapper.EntityMapper;
import com.usermanagement.service.dto.UserActionDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class UserActionMapper extends ModelMapper implements EntityMapper<UserActionDTO, UserAction> {

    @Override
    public UserAction toEntity(UserActionDTO userDTO) {
        return map(userDTO, UserAction.class);
    }

    @Override
    public UserActionDTO toDto(UserAction user) {
        return map(user, UserActionDTO.class);
    }
}
