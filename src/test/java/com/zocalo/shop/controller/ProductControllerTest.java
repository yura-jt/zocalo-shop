package com.zocalo.shop.controller;

import com.zocalo.shop.dto.ProductDto;
import com.zocalo.shop.entity.Product;
import com.zocalo.shop.mapper.ProductDtoMapper;
import com.zocalo.shop.service.ProductService;
import com.zocalo.shop.validator.InputArgumentValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
    private static final Long PRODUCT_ID = 1L;
    private static final String PRODUCT_NAME = "Motorola RAZR V6";
    private static final String PRODUCT_IMAGE_URL = "245.23.123.123/img.webp";

    private final ProductDto PRODUCT_DTO = getProductDto();
    private final Product PRODUCT = getProduct();

    @Mock
    private ProductService productService;

    @Mock
    private InputArgumentValidator<ProductDto> validator;

    @Mock
    private ProductDtoMapper productDtoMapper;

    @InjectMocks
    private ProductController productController;

    @Test
    void getProductShouldReturnProduct() {
        when(productService.getById(any())).thenReturn(PRODUCT);
        when(productDtoMapper.toProductDto(PRODUCT)).thenReturn(PRODUCT_DTO);

        ProductDto actual = productController.getProduct(PRODUCT_ID).getBody();

        assertEquals(PRODUCT_DTO, actual);
    }

    @Test
    void saveProductShouldReturnSavedProduct() {
        ProductDto expected = PRODUCT_DTO;

        ProductDto actual = productController.saveProduct(PRODUCT_DTO).getBody();

        assertEquals(expected, actual);
    }

    @Test
    void deleteProductShouldRemoveProduct() {
        when(productService.getById(any())).thenReturn(PRODUCT);

        productController.deleteProduct(PRODUCT_ID);

        verify(productService).delete(PRODUCT_ID);
    }

    @Test
    void getAllProductsShouldReturnAllProducts() {
        List<Product> products = Collections.singletonList(PRODUCT);
        List<ProductDto> productDtos = Collections.singletonList(PRODUCT_DTO);

        when(productService.getAll()).thenReturn(products);
        when(productDtoMapper.toProductDtos(any())).thenReturn(productDtos);

        productController.getAllProducts().getBody();

        verify(productService).getAll();
    }

    @Test
    void updateProductShouldModifyAndSaveProduct() {
        ProductDto expected = PRODUCT_DTO;
        when(productDtoMapper.toProductDto(any())).thenReturn(PRODUCT_DTO);

        ProductDto actual = productController.updateProduct(PRODUCT_ID, PRODUCT_DTO).getBody();

        assertEquals(expected, actual);
    }

    private ProductDto getProductDto() {
        return ProductDto.builder().id(PRODUCT_ID)
                .name(PRODUCT_NAME)
                .image(PRODUCT_IMAGE_URL)
                .build();
    }

    private Product getProduct() {
        Product product = new Product();
        product.setId(PRODUCT_ID);
        product.setName(PRODUCT_NAME);
        product.setImage(PRODUCT_IMAGE_URL);

        return product;
    }

}
