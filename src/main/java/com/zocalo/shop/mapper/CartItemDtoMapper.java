package com.zocalo.shop.mapper;

import com.zocalo.shop.dto.CartItemDto;
import com.zocalo.shop.entity.CartItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartItemDtoMapper {
    CartItem toCartItem(CartItemDto cartItemDto);

    CartItemDto toCartItemDto(CartItem cartItem);

    List<CartItemDto> toCartItemDtos(List<CartItem> carts);

}
