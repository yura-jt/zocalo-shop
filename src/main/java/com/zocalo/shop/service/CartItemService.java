package com.zocalo.shop.service;

import com.zocalo.shop.entity.Cart;
import com.zocalo.shop.entity.CartItem;

import java.util.List;

public interface CartItemService {

    CartItem getById(Integer id);

    List<CartItem> getAll();

    void save(CartItem cartItem);

    void delete(Integer id);

}
