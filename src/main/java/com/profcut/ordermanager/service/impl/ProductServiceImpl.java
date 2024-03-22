package com.profcut.ordermanager.service.impl;

import com.profcut.ordermanager.controllers.rest.mapper.ProductCreateMapper;
import com.profcut.ordermanager.domain.dto.filter.PageRequest;
import com.profcut.ordermanager.domain.dto.product.CreateProductRequest;
import com.profcut.ordermanager.domain.dto.product.ProductFieldsPatch;
import com.profcut.ordermanager.domain.dto.filter.FilterRequest;
import com.profcut.ordermanager.domain.dto.product.UpdateProductRequest;
import com.profcut.ordermanager.domain.entities.ProductEntity;
import com.profcut.ordermanager.domain.exceptions.ProductNotFoundException;
import com.profcut.ordermanager.domain.repository.ProductRepository;
import com.profcut.ordermanager.service.ProductService;
import com.profcut.ordermanager.utils.jpa.specification.PageConverter;
import com.profcut.ordermanager.utils.jpa.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductCreateMapper productCreateMapper;

    @Override
    @Transactional
    public ProductEntity createProduct(CreateProductRequest request) {
        log.info("invoke ProductServiceImpl#createProduct with request {}", request);
        var newProduct = productCreateMapper.apply(request);
        return productRepository.save(newProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(UUID productId) {
        log.info("invoke ProductServiceImpl#deleteProduct with productId: {}", productId);
        productRepository.deleteById(productId);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductEntity getProductById(UUID productId) {
        return productRepository.findByProductId(productId).orElseThrow(() -> ProductNotFoundException.byProductId(productId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductEntity> findProductByFilter(FilterRequest filter) {
        log.info("invoke ProductServiceImpl#findProductByFilter with filter {}", filter);
        var pageable = PageConverter.covertToPageable(filter.getPageRequest());
        var spec = ProductSpecification.byProductNameLike(filter.search);
        return productRepository.findAll(spec, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductEntity> getProductPage(PageRequest request) {
        log.info("invoke ProductServiceImpl#getProductPage with request {}", request);
        var pageable = PageConverter.covertToPageable(request);
        return productRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public ProductEntity updateProduct(UpdateProductRequest request) {
        log.info("invoke ProductServiceImpl#updateProduct with request: {}", request);
        var productDb = getProductById(request.getProductId());
        updateProductByPatch(productDb, request.getPatch());
        return productRepository.save(productDb);
    }

    private void updateProductByPatch(ProductEntity product, ProductFieldsPatch patch) {
        ofNullable(patch.getProductName()).ifPresent(product::setProductName);
    }
}
