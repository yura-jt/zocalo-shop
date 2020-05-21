package com.zocalo.shop.dto;

import com.zocalo.shop.entity.CartItem;
import com.zocalo.shop.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Builder
@Getter
@Setter
@ToString
public class CartDto {

    private Long id;

    @NonNull
    private User user;

    @NotNull
    private Set<CartItem> cartItems;

}
