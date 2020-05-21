package com.zocalo.shop.service.impl;

import com.zocalo.shop.dto.CartDto;
import com.zocalo.shop.entity.Cart;
import com.zocalo.shop.exception.EntityNotFoundException;
import com.zocalo.shop.mapper.CartDtoMapper;
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
    private final CartDtoMapper cartDtoMapper;

    @Override
    @Transactional(readOnly = true)
    public Cart getById(Long id) {
        Optional<Cart> optionalCart = cartRepository.findById(id);
        if (!optionalCart.isPresent()) {
            String message = String.format("Cart with id = %s was not found", id);
            log.warn(message);
            throw new EntityNotFoundException(message);
        }
        return optionalCart.get();
    }

    @Override
    public Cart save(CartDto cartDto) {
        Cart cart = cartDtoMapper.toCart(cartDto);
        return cartRepository.save(cart);
    }

    @Override
    public void delete(Long id) {
        cartRepository.deleteById(id);
    }

}
