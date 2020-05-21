package com.zocalo.shop.service;

import com.zocalo.shop.dto.ProductDto;
import com.zocalo.shop.entity.Product;

import java.util.List;

public interface ProductService {

    Product getById(Long id);

    List<Product> getAll();

    Product save(ProductDto productDto);

    Product update(Long id, ProductDto productDto);

    void delete(Long id);

}
