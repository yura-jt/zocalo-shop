package com.zocalo.shop.mapper;

import com.zocalo.shop.dto.UserDto;
import com.zocalo.shop.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    User toUser(UserDto userDto);

    UserDto toUserDto(User user);

    List<UserDto> toUserDtos(List<User> users);

}
