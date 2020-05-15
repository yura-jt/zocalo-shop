package com.zocalo.shop.service;

import com.zocalo.shop.entity.User;

import java.util.List;

public interface UserService {

    User getById(Integer id);

    void save(User user);

    void delete(Integer id);

    List<User> getAll();

}
