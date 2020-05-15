package com.zocalo.shop.service.impl;

import com.zocalo.shop.entity.Product;
import com.zocalo.shop.exception.EntityNotFoundException;
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

    @Override
    @Transactional(readOnly = true)
    public Product getById(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            String message = String.format("Product with id = %s was not found", id);
            log.warn(message);
            throw new EntityNotFoundException(message);
        }
        return optionalProduct.get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void delete(Integer id) {
        productRepository.deleteById(id);
    }

}
