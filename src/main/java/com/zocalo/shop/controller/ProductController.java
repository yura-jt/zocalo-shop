package com.zocalo.shop.controller;

import com.zocalo.shop.dto.ProductDto;
import com.zocalo.shop.entity.Product;
import com.zocalo.shop.exception.EntityNotFoundException;
import com.zocalo.shop.mapper.ProductDtoMapper;
import com.zocalo.shop.service.ProductService;
import com.zocalo.shop.validator.InputArgumentValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final InputArgumentValidator<ProductDto> validator;
    private final ProductDtoMapper productDtoMapper;

    @GetMapping(value = "{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long productId) {
        validator.validateId(productId);
        Product product = productService.getById(productId);
        ProductDto productDto = productDtoMapper.toProductDto(product);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDto> saveProduct(@RequestBody @Valid ProductDto productDto) {
        validator.validateEntityForNull(productDto);
        productService.save(productDto);
        return new ResponseEntity<>(productDto, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable("id") Long productId) {
        validator.validateId(productId);
        Product product = productService.getById(productId);

        if (product == null) {
            String message = String.format("Product with id=%s was not deleted, because it doesn't exist in database", productId);
            log.warn(message);
            throw new EntityNotFoundException(message);
        }
        productService.delete(productId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> products = productService.getAll();
        List<ProductDto> productDtos = productDtoMapper.toProductDtos(products);

        if (productDtos.isEmpty()) {
            String message = "Requested Products is empty";
            log.warn(message);
            throw new EntityNotFoundException(message);
        }

        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
        validator.validateEntityForNull(productDto);
        Product newProduct = productService.update(id, productDto);
        ProductDto newProductDto = productDtoMapper.toProductDto(newProduct);
        return new ResponseEntity<>(newProductDto, HttpStatus.OK);
    }

}
