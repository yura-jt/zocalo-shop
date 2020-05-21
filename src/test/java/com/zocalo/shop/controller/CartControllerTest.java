package com.zocalo.shop.controller;

import com.zocalo.shop.dto.CartDto;
import com.zocalo.shop.entity.Cart;
import com.zocalo.shop.entity.User;
import com.zocalo.shop.mapper.CartDtoMapper;
import com.zocalo.shop.service.CartService;
import com.zocalo.shop.validator.InputArgumentValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {
    private static final Long CART_ID = 1L;

    private final CartDto CART_DTO = getCartDto();
    private final Cart CART = getCart();

    @Mock
    private CartService cartService;

    @Mock
    private InputArgumentValidator<CartDto> validator;

    @Mock
    private CartDtoMapper cartDtoMapper;

    @InjectMocks
    private CartController cartController;

    @Test
    void getCartShouldReturnCart() {
        when(cartService.getById(any())).thenReturn(CART);
        when(cartDtoMapper.toCartDto(CART)).thenReturn(CART_DTO);

        CartDto actual = cartController.getCart(CART_ID).getBody();

        assertEquals(CART_DTO, actual);
    }

    @Test
    void saveCartShouldReturnSavedCart() {
        CartDto expected = CART_DTO;

        CartDto actual = cartController.saveCart(CART_DTO).getBody();

        assertEquals(expected, actual);
    }

    @Test
    void deleteCartShouldRemoveCart() {
        when(cartService.getById(any())).thenReturn(CART);

        cartController.deleteCart(CART_ID);

        verify(cartService).delete(CART_ID);
    }


    private CartDto getCartDto() {
        User user = new User();
        user.setId(CART_ID);
        return CartDto.builder().id(CART_ID)
                .cartItems(new HashSet<>())
                .user(user)
                .build();
    }

    private Cart getCart() {
        User user = new User();
        Cart cart = new Cart();
        cart.setId(CART_ID);
        cart.setCartItems(new HashSet<>());
        cart.setUser(user);
        return cart;
    }

}
