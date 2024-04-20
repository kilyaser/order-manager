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
import com.profcut.ordermanager.utils.jpa.specification.PageConverter;
import com.profcut.ordermanager.utils.jpa.specification.ProductSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    ProductRepository productRepository;
    @Mock
    ProductCreateMapper productCreateMapper;
    @InjectMocks
    ProductServiceImpl productService;

    @Test
    @DisplayName("Успешное создание продукта")
    void createProduct() {
        var name = "newProduct";
        var request = new CreateProductRequest(name);
        var entity = new ProductEntity()
                .setProductName(name);

        when(productCreateMapper.apply(request)).thenReturn(entity);
        when(productRepository.save(entity)).thenReturn(entity.setProductId(UUID.randomUUID()));

        var result = productService.createProduct(request);

        assertNotNull(result);
        verify(productCreateMapper).apply(request);
        verify(productRepository).save(entity);
    }

    @Test
    @DisplayName("Удаление продукта")
    void deleteProduct() {
        var id = UUID.randomUUID();

        assertThatCode(() -> productService.deleteProduct(id)).doesNotThrowAnyException();

        verify(productRepository).deleteById(id);
    }

    @Test
    @DisplayName("Успешное получение продукта по id")
    void getProductById() {
        var id = UUID.randomUUID();
        var entity = new ProductEntity().setProductId(id).setProductName("ProductDb");

        when(productRepository.findByProductId(id)).thenReturn(Optional.of(entity));

        var result = productService.getProductById(id);

        assertNotNull(result);
        verify(productRepository).findByProductId(id);
    }

    @Test
    @DisplayName("Ошибка получения продукта по id")
    void getProductById_exception() {
        var id = UUID.randomUUID();

        when(productRepository.findByProductId(id)).thenReturn(Optional.empty());

        assertThatCode(() -> productService.getProductById(id)).isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Получение продукта по фильту")
    void findProductByFilter() {
        var filter = FilterRequest.builder()
                .search("productName")
                .pageRequest(new PageRequest(0, 20))
                .build();
        var products = Stream.of("ProductName", "ProductName2")
                .map(name -> new ProductEntity()
                        .setProductId(UUID.randomUUID())
                        .setProductName(name))
                .toList();
        var spec = ProductSpecification.byProductNameLike(filter.search);
        var pageable = PageConverter.covertToPageable(filter.getPageRequest());
        Page<ProductEntity> productsPage = new PageImpl<>(products, pageable, products.size());

        when(productRepository.findAll(any(spec.getClass()), any(Pageable.class))).thenReturn(productsPage);

        assertThatCode(() -> productService.findProductByFilter(filter)).doesNotThrowAnyException();

        verify(productRepository).findAll(any(spec.getClass()), any(Pageable.class));
    }

    @Test
    @DisplayName("Успешное получение страниы продукции")
    void getProductPage() {
        var request = new PageRequest().setPage(0).setSize(20);

        assertThatCode(() -> productService.getProductPage(request)).doesNotThrowAnyException();

        verify(productRepository).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Обновление продукта")
    void updateProduct() {
        var id = UUID.randomUUID();
        var request = new UpdateProductRequest()
                .setProductId(id)
                .setPatch(new ProductFieldsPatch("UpdateProduct"));
        var entity = new ProductEntity().setProductId(id).setProductName(request.getPatch().getProductName());

        when(productRepository.findByProductId(id)).thenReturn(Optional.ofNullable(entity));

        assertThatCode(() -> productService.updateProduct(request)).doesNotThrowAnyException();

        verify(productRepository).findByProductId(id);
        verify(productRepository).save(any(ProductEntity.class));
    }
}
