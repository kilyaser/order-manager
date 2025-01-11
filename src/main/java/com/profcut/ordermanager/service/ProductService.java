package com.profcut.ordermanager.service;

import com.profcut.ordermanager.domain.dto.filter.PageRequest;
import com.profcut.ordermanager.domain.dto.filter.SearchRequest;
import com.profcut.ordermanager.domain.dto.product.CreateProductRequest;
import com.profcut.ordermanager.domain.dto.filter.FilterRequest;
import com.profcut.ordermanager.domain.dto.product.UpdateProductRequest;
import com.profcut.ordermanager.domain.entities.ProductEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductEntity createProduct(CreateProductRequest request);

    void deleteProduct(UUID productId);

    ProductEntity getProductById(UUID productId);

    Page<ProductEntity> findProductByFilter(FilterRequest filter);

    List<ProductEntity> getProducts(SearchRequest search);

    Page<ProductEntity> getProductPage(PageRequest request);

    ProductEntity updateProduct(UpdateProductRequest request);
}
