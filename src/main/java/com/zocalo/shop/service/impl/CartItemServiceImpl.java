package com.zocalo.shop.service.impl;

import com.zocalo.shop.entity.CartItem;
import com.zocalo.shop.exception.EntityNotFoundException;
import com.zocalo.shop.repository.CartItemRepository;
import com.zocalo.shop.service.CartItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;

    @Override
    @Transactional(readOnly = true)
    public CartItem getById(Integer id) {
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(id);
        if (!optionalCartItem.isPresent()) {
            String message = String.format("CartItem with id = %s was not found", id);
            log.warn(message);
            throw new EntityNotFoundException(message);
        }
        return optionalCartItem.get();
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
