package com.star.altineller_kuyumcu.service.impl;

import com.star.altineller_kuyumcu.dto.ProductDto;
import com.star.altineller_kuyumcu.dto.ProductDtoIU;
import com.star.altineller_kuyumcu.exception.BaseException;
import com.star.altineller_kuyumcu.exception.ResourceNotFoundException;
import com.star.altineller_kuyumcu.exception.MessageType;
import com.star.altineller_kuyumcu.model.Category;
import com.star.altineller_kuyumcu.model.Product;
import com.star.altineller_kuyumcu.repository.CategoryRepository;
import com.star.altineller_kuyumcu.repository.ProductRepository;
import com.star.altineller_kuyumcu.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public ProductDto createProduct(ProductDtoIU productDtoIU) {
        if (productRepository.existsByName(productDtoIU.getName())) {
            throw new BaseException(MessageType.PRODUCT_ALREADY_EXISTS);
        }

        Category category = categoryRepository.findById(productDtoIU.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(MessageType.CATEGORY_NOT_FOUND));

        Product product = modelMapper.map(productDtoIU, Product.class);
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }

    @Override
    @Transactional
    public ProductDto updateProduct(Long id, ProductDtoIU productDtoIU) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageType.PRODUCT_NOT_FOUND));

        Category category = categoryRepository.findById(productDtoIU.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(MessageType.CATEGORY_NOT_FOUND));

        modelMapper.map(productDtoIU, product);
        product.setCategory(category);

        Product updatedProduct = productRepository.save(product);
        return convertToDto(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageType.PRODUCT_NOT_FOUND));
        return convertToDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategory(String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new RuntimeException("Category not found: " + categoryName));

        return productRepository.findByCategory(category).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getLowStockProducts(Integer threshold) {
        return productRepository.findByStockQuantityLessThan(threshold).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isProductExists(String name) {
        return productRepository.existsByName(name);
    }

    private ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        productDto.setCategory(product.getCategory().getCategoryName());
        return productDto;
    }
}