package com.zocalo.shop.controller;

import com.zocalo.shop.dto.UserDto;
import com.zocalo.shop.entity.User;
import com.zocalo.shop.exception.EntityNotFoundException;
import com.zocalo.shop.mapper.UserDtoMapper;
import com.zocalo.shop.service.UserService;
import com.zocalo.shop.validator.InputArgumentValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final InputArgumentValidator<UserDto> validator;
    private final UserDtoMapper userDtoMapper;

    @GetMapping(value = "{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long userId) {
        validator.validateId(userId);
        User user = userService.getById(userId);
        UserDto userDto = userDtoMapper.toUserDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody @Valid UserDto userDto) {
        validator.validateEntityForNull(userDto);
        userService.save(userDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable("id") Long userId) {
        validator.validateId(userId);
        User user = userService.getById(userId);

        if (user == null) {
            String message = String.format("User with id=%s was not deleted, because it doesn't exist in database", userId);
            log.warn(message);
            throw new EntityNotFoundException(message);
        }
        userService.delete(userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.getAll();
        List<UserDto> userDtos = userDtoMapper.toUserDtos(users);

        if (userDtos.isEmpty()) {
            String message = "Requested Users is empty";
            log.warn(message);
            throw new EntityNotFoundException(message);
        }

        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        validator.validateEntityForNull(userDto);
        User newUser = userService.update(id, userDto);
        UserDto newUserDto = userDtoMapper.toUserDto(newUser);
        return new ResponseEntity<>(newUserDto, HttpStatus.OK);
    }

}
