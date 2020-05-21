package com.zocalo.shop.controller;

import com.zocalo.shop.dto.CartDto;
import com.zocalo.shop.entity.Cart;
import com.zocalo.shop.exception.EntityNotFoundException;
import com.zocalo.shop.mapper.CartDtoMapper;
import com.zocalo.shop.service.CartService;
import com.zocalo.shop.validator.InputArgumentValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;
    private final InputArgumentValidator<CartDto> validator;
    private final CartDtoMapper cartDtoMapper;

    @GetMapping(value = "{id}")
    public ResponseEntity<CartDto> getCart(@PathVariable("id") Long cartId) {
        validator.validateId(cartId);
        Cart cart = cartService.getById(cartId);
        CartDto cartDto = cartDtoMapper.toCartDto(cart);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CartDto> saveCart(@RequestBody @Valid CartDto cartDto) {
        validator.validateEntityForNull(cartDto);
        cartService.save(cartDto);
        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<CartDto> deleteCart(@PathVariable("id") Long cartId) {
        validator.validateId(cartId);
        Cart cart = cartService.getById(cartId);

        if (cart == null) {
            String message = String.format("Cart with id=%s was not deleted, because it doesn't exist in database", cartId);
            log.warn(message);
            throw new EntityNotFoundException(message);
        }
        cartService.delete(cartId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
