package com.zocalo.shop.service;

import com.zocalo.shop.dto.CartItemDto;
import com.zocalo.shop.entity.CartItem;

import java.util.List;

public interface CartItemService {

    CartItem getById(Long id);

    CartItem save(CartItemDto cartItemDto);

    List<CartItem> findCartItemByCartId(Long cartId);

    void delete(Long id);

}
