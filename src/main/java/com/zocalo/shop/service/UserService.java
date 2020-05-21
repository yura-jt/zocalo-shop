package com.zocalo.shop.service;

import com.zocalo.shop.dto.UserDto;
import com.zocalo.shop.entity.User;

import java.util.List;

public interface UserService {

    User getById(Long id);

    User save(UserDto userDto);

    void delete(Long id);

    User update(Long id, UserDto userDto);

    List<User> getAll();

}
