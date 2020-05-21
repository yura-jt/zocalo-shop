package com.zocalo.shop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Builder
@Getter
@Setter
@ToString
public class ProductDto {
    private Long id;

    @NotEmpty
    private String name;

    private String image;

    @Min(value = 0)
    private BigDecimal price;

}
