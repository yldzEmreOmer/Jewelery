package com.star.altineller_kuyumcu.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDtoIU {
    @NotBlank(message = "Ürün adı boş bırakılamaz.")
    private String name;

    @NotNull(message = "Fiyat belirtilmelidir.")
    @Min(value = 0, message = "Fiyat negatif olamaz.")
    private Double price;

    @NotBlank(message = "Kategori boş bırakılamaz.")
    private String category;

    private Long categoryId;

    @NotNull(message = "Stok adedi belirtilmelidir.")
    @Min(value = 0, message = "Stok adedi negatif olamaz.")
    private Integer stock;

    private String description;
    private String imageUrl;
}