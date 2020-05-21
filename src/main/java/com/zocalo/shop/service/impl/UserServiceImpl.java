package com.zocalo.shop.service.impl;

import com.zocalo.shop.dto.UserDto;
import com.zocalo.shop.entity.User;
import com.zocalo.shop.exception.EntityNotFoundException;
import com.zocalo.shop.mapper.UserDtoMapper;
import com.zocalo.shop.repository.UserRepository;
import com.zocalo.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
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

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findUserByEmail(username);
        if (user == null) {
            log.warn("Security exception: user was not found with credential:" + username);
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority());
    }

    private List getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

}
