package com.zocalo.shop.dto;

import com.zocalo.shop.entity.Cart;
import com.zocalo.shop.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Builder
@Getter
@Setter
@ToString
public class CartItemDto {

    private Long id;

    @NotEmpty
    private Integer quantity;

    @NotEmpty
    private Product product;

    @NotEmpty
    private Cart cart;

}
