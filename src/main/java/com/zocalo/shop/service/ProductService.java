package com.zocalo.shop.service;

import com.zocalo.shop.entity.Product;

import java.util.List;

public interface ProductService {

    Product getById(Integer id);

    List<Product> getAll();

    void save(Product product);

    void delete(Integer id);

}
