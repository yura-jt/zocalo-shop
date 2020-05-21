package com.zocalo.shop.controller;

import com.zocalo.shop.dto.UserDto;
import com.zocalo.shop.entity.RoleType;
import com.zocalo.shop.entity.User;
import com.zocalo.shop.mapper.UserDtoMapper;
import com.zocalo.shop.service.UserService;
import com.zocalo.shop.validator.InputArgumentValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    private static final Long USER_ID = 1L;
    private static final String FIRST_NAME = "Teatime";
    private static final String LAST_NAME = "Brungildushten";
    private static final String EMAIL = "tea.199@gmail.com";
    private static final String PHONE_NUMBER = "+374568445216";
    private static final String PASSWORD = "pass6789";
    private static final RoleType USER_ROLE = RoleType.USER;

    private final UserDto USER_DTO = getUserDto();
    private final User USER = getUser();

    @Mock
    private UserService userService;

    @Mock
    private InputArgumentValidator<UserDto> validator;

    @Mock
    private UserDtoMapper userDtoMapper;

    @InjectMocks
    private UserController userController;

    @Test
    void getUserShouldReturnUser() {
        when(userService.getById(any())).thenReturn(USER);
        when(userDtoMapper.toUserDto(USER)).thenReturn(USER_DTO);

        UserDto actual = userController.getUser(USER_ID).getBody();

        assertEquals(USER_DTO, actual);
    }

    @Test
    void saveUserShouldReturnSavedUser() {
        UserDto expected = USER_DTO;

        UserDto actual = userController.saveUser(USER_DTO).getBody();

        assertEquals(expected, actual);
    }

    @Test
    void deleteUserShouldRemoveUser() {
        when(userService.getById(any())).thenReturn(USER);

        userController.deleteUser(USER_ID);

        verify(userService).delete(USER_ID);
    }

    @Test
    void getAllUsersShouldReturnAllUsers() {
        List<User> users = Collections.singletonList(USER);
        List<UserDto> userDtos = Collections.singletonList(USER_DTO);

        when(userService.getAll()).thenReturn(users);
        when(userDtoMapper.toUserDtos(any())).thenReturn(userDtos);

        userController.getAllUsers().getBody();

        verify(userService).getAll();
    }

    @Test
    void updateUserShouldModifyAndSaveUser() {
        UserDto expected = USER_DTO;
        when(userDtoMapper.toUserDto(any())).thenReturn(USER_DTO);

        UserDto actual = userController.updateUser(USER_ID, USER_DTO).getBody();

        assertEquals(expected, actual);
    }

    private UserDto getUserDto() {
        return UserDto.builder().id(USER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .phoneNumber(PHONE_NUMBER)
                .password(PASSWORD)
                .roleType(USER_ROLE)
                .build();
    }

    private User getUser() {
        User user = new User();
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setEmail(EMAIL);
        user.setPhoneNumber(PHONE_NUMBER);
        user.setPassword(PASSWORD);
        user.setRoleType(USER_ROLE);

        return user;
    }

}
