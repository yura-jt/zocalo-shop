package com.zocalo.shop.service.impl;

import com.zocalo.shop.dto.ProductDto;
import com.zocalo.shop.entity.Product;
import com.zocalo.shop.exception.EntityNotFoundException;
import com.zocalo.shop.mapper.ProductDtoMapper;
import com.zocalo.shop.repository.ProductRepository;
import com.zocalo.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductDtoMapper productDtoMapper;

    @Override
    @Transactional(readOnly = true)
    public Product getById(Long id) {
        return findProductById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product save(ProductDto productDto) {
        Product product = productDtoMapper.toProduct(productDto);
        return productRepository.save(product);
    }

    @Override
    public Product update(Long id, ProductDto productDto) {
        findProductById(id);
        productDto.setId(id);
        Product updatedProduct = productDtoMapper.toProduct(productDto);
        return productRepository.save(updatedProduct);
    }


    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    private Product findProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            String message = String.format("Product with id = %s was not found", id);
            log.warn(message);
            throw new EntityNotFoundException(message);
        }
        return optionalProduct.get();
    }

}
