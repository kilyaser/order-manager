package com.profcut.ordermanager.controllers.rest.ui;

import com.profcut.ordermanager.controllers.rest.mapper.UiProductMapper;
import com.profcut.ordermanager.domain.dto.filter.PageRequest;
import com.profcut.ordermanager.domain.dto.filter.SearchRequest;
import com.profcut.ordermanager.domain.dto.product.CreateProductRequest;
import com.profcut.ordermanager.domain.dto.filter.FilterRequest;
import com.profcut.ordermanager.domain.dto.product.UiProduct;
import com.profcut.ordermanager.domain.dto.product.UiProducts;
import com.profcut.ordermanager.domain.dto.product.UpdateProductRequest;
import com.profcut.ordermanager.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ui/products")
@Tag(name = "product-ui-api", description = "Контроллер для управления информации о изделиях.")
public class ProductApi {

    private final ProductService productService;
    private final UiProductMapper uiProductMapper;

    @GetMapping("/{productId}")
    @Operation(description = "Получить информацию об изделии.")
    public UiProduct getProduct(@PathVariable("productId") UUID productId) {
        return uiProductMapper.apply(productService.getProductById(productId));
    }

    @PostMapping("/find")
    @Operation(description = "Поиск изделий по наименованию с пагинацией")
    public Page<UiProduct> findProductByFilter(@RequestBody FilterRequest filter) {
        return productService.findProductByFilter(filter).map(uiProductMapper);
    }

    @PostMapping("/search")
    @Operation(description = "Поиск изделий по наименованию")
    public UiProducts searchProducts(@Valid @RequestBody SearchRequest search) {
        return productService.getProducts(search).stream()
                .map(uiProductMapper)
                .collect(Collectors.collectingAndThen(Collectors.toList(), UiProducts::new));
    }

    @PostMapping("/page")
    @Operation(description = "Получить страницу изделний")
    public Page<UiProduct> getProductPage(@RequestBody PageRequest request) {
        return productService.getProductPage(request).map(uiProductMapper);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Создать изделие.")
    public UiProduct createProduct(@Valid @RequestBody CreateProductRequest request) {
        return uiProductMapper.apply(productService.createProduct(request));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Обновить информацию изделия")
    public UiProduct updateProduct(@Valid @RequestBody UpdateProductRequest request) {
        return uiProductMapper.apply(productService.updateProduct(request));
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Удалить продукт")
    public void deleteProduct(@PathVariable("productId") UUID productId) {
        productService.deleteProduct(productId);
    }
}
