package com.zocalo.shop.mapper;

import com.zocalo.shop.dto.CartDto;
import com.zocalo.shop.entity.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartDtoMapper {
    Cart toCart(CartDto cartDto);

    CartDto toCartDto(Cart cart);
}
