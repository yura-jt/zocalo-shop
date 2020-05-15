package com.zocalo.shop.service.impl;

import com.zocalo.shop.entity.Cart;
import com.zocalo.shop.exception.EntityNotFoundException;
import com.zocalo.shop.repository.CartRepository;
import com.zocalo.shop.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;

    @Override
    @Transactional(readOnly = true)
    public Cart getById(Integer id) {
        Optional<Cart> optionalCart = cartRepository.findById(id);
        if (!optionalCart.isPresent()) {
            String message = String.format("Cart with id = %s was not found", id);
            log.warn(message);
            throw new EntityNotFoundException(message);
        }
        return optionalCart.get();
    }

    @Override
    public void save(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public void delete(Integer id) {
        cartRepository.deleteById(id);
    }

}
