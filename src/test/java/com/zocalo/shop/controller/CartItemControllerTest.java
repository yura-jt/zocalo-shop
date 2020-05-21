package com.zocalo.shop.controller;

import com.zocalo.shop.dto.CartItemDto;
import com.zocalo.shop.entity.Cart;
import com.zocalo.shop.entity.CartItem;
import com.zocalo.shop.entity.Product;
import com.zocalo.shop.mapper.CartItemDtoMapper;
import com.zocalo.shop.service.CartItemService;
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
class CartItemControllerTest {
    private static final Long CART_ITEM_ID = 1L;
    private static final Long CART_ID = 2L;

    private final CartItemDto CART_ITEM_DTO = getCartItemDto();
    private final CartItem CART_ITEM = getCartItem();

    @Mock
    private CartItemService cartItemService;

    @Mock
    private InputArgumentValidator<CartItemDto> validator;

    @Mock
    private CartItemDtoMapper cartItemDtoMapper;

    @InjectMocks
    private CartItemController cartItemController;

    @Test
    void getCartItemShouldReturnCartItem() {
        when(cartItemService.getById(any())).thenReturn(CART_ITEM);
        when(cartItemDtoMapper.toCartItemDto(CART_ITEM)).thenReturn(CART_ITEM_DTO);

        CartItemDto actual = cartItemController.getCartItem(CART_ID, CART_ITEM_ID).getBody();

        assertEquals(CART_ITEM_DTO, actual);
    }

    @Test
    void saveCartItemShouldReturnSavedCartItem() {
        CartItemDto expected = CART_ITEM_DTO;

        CartItemDto actual = cartItemController.saveCartItem(CART_ITEM_DTO).getBody();

        assertEquals(expected, actual);
    }

    @Test
    void deleteCartItemShouldRemoveCartItem() {
        when(cartItemService.getById(any())).thenReturn(CART_ITEM);

        cartItemController.deleteCartItem(CART_ITEM_ID);

        verify(cartItemService).delete(CART_ITEM_ID);
    }

    @Test
    void getAllCartItemsShouldReturnAllCartItemByCartId() {
        List<CartItem> cartItems = Collections.singletonList(CART_ITEM);
        List<CartItemDto> cartItemDtos = Collections.singletonList(CART_ITEM_DTO);

        when(cartItemService.findCartItemByCartId(any())).thenReturn(cartItems);
        when(cartItemDtoMapper.toCartItemDtos(any())).thenReturn(cartItemDtos);

        cartItemController.getAllCartItems(CART_ID).getBody();

        verify(cartItemService).findCartItemByCartId(CART_ID);
    }

    private CartItemDto getCartItemDto() {
        Cart cart = new Cart();
        cart.setId(CART_ID);
        Product product = new Product();
        product.setName("Motorola RAZR V6");

        return CartItemDto.builder().id(CART_ITEM_ID)
                .quantity(10)
                .product(product)
                .cart(cart)
                .build();
    }

    private CartItem getCartItem() {
        Cart cart = new Cart();
        cart.setId(CART_ID);
        Product product = new Product();
        product.setName("Motorola RAZR V6");

        CartItem cartItem = new CartItem();
        cartItem.setId(CART_ITEM_ID);
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(10);

        return cartItem;
    }

}
