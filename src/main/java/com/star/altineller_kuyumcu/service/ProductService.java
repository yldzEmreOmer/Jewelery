package com.star.altineller_kuyumcu.service;

import com.star.altineller_kuyumcu.dto.ProductDto;
import com.star.altineller_kuyumcu.dto.ProductDtoIU;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDtoIU productDtoIU);

    ProductDto updateProduct(Long id, ProductDtoIU productDtoIU);

    void deleteProduct(Long id);

    ProductDto getProductById(Long id);

    List<ProductDto> getAllProducts();

    List<ProductDto> getProductsByCategory(String categoryName);

    List<ProductDto> getProductsByPriceRange(Double minPrice, Double maxPrice);

    List<ProductDto> getLowStockProducts(Integer threshold);

    boolean isProductExists(String name);
}