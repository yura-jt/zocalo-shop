package com.zocalo.shop.dto;

import com.zocalo.shop.entity.RoleType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Builder
@Getter
@Setter
@ToString(exclude = {"password"})
public class UserDto {
    private Long id;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @Email
    private String email;

    @NotEmpty
    private String phoneNumber;

    @NotEmpty
    private String password;

    @NotEmpty
    private RoleType roleType;

}
