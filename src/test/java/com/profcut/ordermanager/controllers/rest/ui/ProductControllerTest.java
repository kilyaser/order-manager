package com.profcut.ordermanager.controllers.rest.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profcut.ordermanager.controllers.exception.ErrorHttpResponseFactory;
import com.profcut.ordermanager.controllers.rest.mapper.UiProductMapper;
import com.profcut.ordermanager.domain.dto.product.CreateProductRequest;
import com.profcut.ordermanager.domain.dto.product.ProductFieldsPatch;
import com.profcut.ordermanager.domain.dto.filter.FilterRequest;
import com.profcut.ordermanager.domain.dto.product.UiProduct;
import com.profcut.ordermanager.domain.dto.product.UpdateProductRequest;
import com.profcut.ordermanager.domain.entities.ProductEntity;
import com.profcut.ordermanager.domain.exceptions.ProductNotFoundException;
import com.profcut.ordermanager.security.service.JwtUserService;
import com.profcut.ordermanager.service.ProductService;
import com.profcut.ordermanager.utils.jpa.specification.PageConverter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "user")
@WebMvcTest(ProductController.class)
@Import(ErrorHttpResponseFactory.class)
public class ProductControllerTest {

    @MockBean
    ProductService productService;
    @MockBean
    UiProductMapper uiProductMapper;
    @MockBean
    JwtUserService jwtUserService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    @Test
    @SneakyThrows
    @DisplayName("Успешно получить изделие по id")
    void getProduct_success() {
        var id = UUID.randomUUID();
        var productName = "productFromDb";
        var dto = new UiProduct(id, productName);

        when(productService.getProductById(id)).thenReturn(new ProductEntity().setProductId(id).setProductName(productName));
        when(uiProductMapper.apply(any())).thenReturn(dto);

        mockMvc.perform(get("/api/v1/ui/products/{productId}", id))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        content().string(objectMapper.writeValueAsString(dto))
                );

        verify(productService).getProductById(id);
        verify(uiProductMapper).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Продукт по id не найден")
    void getProduct_exception() {
        var id = UUID.randomUUID();

        when(productService.getProductById(id)).thenThrow(ProductNotFoundException.class);

        mockMvc.perform(get("/api/v1/ui/products/{productId}", id))
                .andExpect(status().isNotFound());

        verify(productService).getProductById(id);
        verify(uiProductMapper, never()).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Поиск продукта по фильтру")
    void findProductByFilter() {
        var filter = FilterRequest.builder()
                .search("productName")
                .build();
        var products = Stream.of("ProductName", "ProductName2")
                .map(name -> new ProductEntity()
                        .setProductId(UUID.randomUUID())
                        .setProductName(name))
                .toList();
        var pageRequest = PageRequest.of(0, 20);
        Page<ProductEntity> productsPage = new PageImpl<>(products, pageRequest, products.size());

        when(productService.findProductByFilter(filter)).thenReturn(productsPage);

        mockMvc.perform(post("/api/v1/ui/products/find")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filter)))
                .andExpect(status().is2xxSuccessful());

        verify(productService).findProductByFilter(any());
        verify(uiProductMapper, times(2)).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Получение страницы продуктов")
    void getProductByPage() {
        var request = new com.profcut.ordermanager.domain.dto.filter.PageRequest()
                .setPage(0).setSize(20);
        var products = Stream.of("ProductName", "ProductName2")
                .map(name -> new ProductEntity()
                        .setProductId(UUID.randomUUID())
                        .setProductName(name))
                .toList();
        var pageRequest = PageConverter.covertToPageable(request);
        Page<ProductEntity> productsPage = new PageImpl<>(products, pageRequest, products.size());

        when(productService.getProductPage(request)).thenReturn(productsPage);

        mockMvc.perform(post("/api/v1/ui/products/page")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful());

        verify(productService).getProductPage(any());
        verify(uiProductMapper, times(2)).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Успешное создание продукта")
    void createProduct_success() {
        var name = "newProduct";
        var request = new CreateProductRequest(name);
        var id = UUID.randomUUID();
        var savedProduct = new ProductEntity()
                .setProductId(id)
                .setProductName(name);
        var dto = new UiProduct(id, name);

        when(productService.createProduct(request)).thenReturn(savedProduct);
        when(uiProductMapper.apply(savedProduct)).thenReturn(dto);

        mockMvc.perform(post("/api/v1/ui/products", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(
                        status().isCreated(),
                        content().string(objectMapper.writeValueAsString(dto))
                );

        verify(productService).createProduct(request);
        verify(uiProductMapper).apply(savedProduct);
    }

    @Test
    @SneakyThrows
    @DisplayName("Ошибка валидации при создании продукта")
    void createProduct_exception() {
        var request = new CreateProductRequest(null);

        mockMvc.perform(post("/api/v1/ui/products", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()),
                        jsonPath("$.exClass").value("MethodArgumentNotValidException"));

        verify(productService, never()).createProduct(any());
        verify(uiProductMapper, never()).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Успешное обновление продукта")
    void updateProduct_success() {
        var request = new UpdateProductRequest()
                .setProductId(UUID.randomUUID())
                .setPatch(new ProductFieldsPatch("updatedName"));

        mockMvc.perform(put("/api/v1/ui/products", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful());

        verify(productService).updateProduct(any());
        verify(uiProductMapper).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Ошибка обновления продукта, patch = null")
    void updateProduct_exception() {
        var request = new UpdateProductRequest()
                .setProductId(UUID.randomUUID())
                .setPatch(null);

        mockMvc.perform(put("/api/v1/ui/products", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()),
                        jsonPath("$.exClass").value("MethodArgumentNotValidException"));

        verify(productService, never()).updateProduct(any());
        verify(uiProductMapper, never()).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Ошибка обновления продукта, patch.productName = null")
    void updateProduct_patch_exception() {
        var request = new UpdateProductRequest()
                .setProductId(UUID.randomUUID())
                .setPatch(new ProductFieldsPatch(null));

        mockMvc.perform(put("/api/v1/ui/products", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());

        verify(productService, never()).updateProduct(any());
        verify(uiProductMapper, never()).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Успешное удаление продукат")
    void deleteProduct() {
        var id = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/ui/products/{productId}", id)
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(productService).deleteProduct(id);
    }
}
