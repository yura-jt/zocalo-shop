package com.zocalo.shop.mapper;

import com.zocalo.shop.dto.ProductDto;
import com.zocalo.shop.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductDtoMapper {

    Product toProduct(ProductDto productDto);

    ProductDto toProductDto(Product product);

    List<ProductDto> toProductDtos(List<Product> products);

}
