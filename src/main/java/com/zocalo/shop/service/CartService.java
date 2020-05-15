package com.zocalo.shop.service;

import com.zocalo.shop.entity.Cart;

public interface CartService {

    Cart getById(Integer id);

    void save(Cart cart);

    void delete(Integer id);

}
