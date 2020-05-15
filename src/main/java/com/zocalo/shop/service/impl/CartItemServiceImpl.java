package com.zocalo.shop.service.impl;

import com.zocalo.shop.entity.CartItem;
import com.zocalo.shop.exception.EntityNotFoundException;
import com.zocalo.shop.repository.CartItemRepository;
import com.zocalo.shop.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;

    @Override
    @Transactional(readOnly = true)
    public CartItem getById(Integer id) {
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(id);
        return optionalCartItem.orElseThrow(() -> new EntityNotFoundException("User was not found by id = " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartItem> getAll() {
        return cartItemRepository.findAll();
    }

    @Override
    public void save(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }

    @Override
    public void delete(Integer id) {
        cartItemRepository.deleteById(id);
    }

}
