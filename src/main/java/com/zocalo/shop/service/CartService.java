package com.zocalo.shop.service;

import com.zocalo.shop.dto.CartDto;
import com.zocalo.shop.entity.Cart;

public interface CartService {

    Cart getById(Long id);

    Cart save(CartDto cartDto);

    void delete(Long id);

}
