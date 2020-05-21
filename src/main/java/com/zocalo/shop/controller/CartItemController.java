package com.zocalo.shop.controller;

import com.zocalo.shop.dto.CartItemDto;
import com.zocalo.shop.entity.CartItem;
import com.zocalo.shop.exception.EntityNotFoundException;
import com.zocalo.shop.mapper.CartItemDtoMapper;
import com.zocalo.shop.service.CartItemService;
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
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/carts/{cartId}/cart-items")
public class CartItemController {
    private final CartItemService cartItemService;
    private final InputArgumentValidator<CartItemDto> validator;
    private final CartItemDtoMapper cartItemDtoMapper;

    @GetMapping(value = "{id}")
    public ResponseEntity<CartItemDto> getCartItem(@PathVariable("cartId") Long cartId,
                                                   @PathVariable("id") Long cartItemId) {
        validator.validateId(cartItemId);
        CartItem cartItem = cartItemService.getById(cartItemId);
        CartItemDto cartItemDto = cartItemDtoMapper.toCartItemDto(cartItem);
        return new ResponseEntity<>(cartItemDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CartItemDto> saveCartItem(@RequestBody @Valid CartItemDto cartItemDto) {
        validator.validateEntityForNull(cartItemDto);
        cartItemService.save(cartItemDto);
        return new ResponseEntity<>(cartItemDto, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<CartItemDto> deleteCartItem(@PathVariable("id") Long cartItemId) {
        validator.validateId(cartItemId);
        CartItem cartItem = cartItemService.getById(cartItemId);

        if (cartItem == null) {
            String message = String.format("CartItem with id=%s was not deleted, because it doesn't exist in database", cartItemId);
            log.warn(message);
            throw new EntityNotFoundException(message);
        }
        cartItemService.delete(cartItemId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<CartItemDto>> getAllCartItems(@PathVariable("cartId") Long cartId) {
        validator.validateId(cartId);
        List<CartItem> cartItems = cartItemService.findCartItemByCartId(cartId);
        List<CartItemDto> cartItemDtos = cartItemDtoMapper.toCartItemDtos(cartItems);

        if (cartItemDtos.isEmpty()) {
            String message = "Requested CartItems is empty";
            log.warn(message);
            throw new EntityNotFoundException(message);
        }

        return new ResponseEntity<>(cartItemDtos, HttpStatus.OK);
    }

}
