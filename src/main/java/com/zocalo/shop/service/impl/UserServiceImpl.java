package com.zocalo.shop.service.impl;

import com.zocalo.shop.dto.UserDto;
import com.zocalo.shop.entity.User;
import com.zocalo.shop.exception.EntityNotFoundException;
import com.zocalo.shop.mapper.UserDtoMapper;
import com.zocalo.shop.repository.UserRepository;
import com.zocalo.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) {
        return findUserById(id);
    }

    @Override
    public User save(UserDto userDto) {
        User user = userDtoMapper.toUser(userDto);
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User update(Long id, UserDto userDto) {
        findUserById(id);
        userDto.setId(id);
        User updatedUser = userDtoMapper.toUser(userDto);
        return userRepository.save(updatedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    private User findUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            String message = String.format("User with id = %s was not found", id);
            log.warn(message);
            throw new EntityNotFoundException(message);
        }
        return optionalUser.get();
    }
}
