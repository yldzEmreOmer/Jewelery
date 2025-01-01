package com.star.altineller_kuyumcu.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDtoIU {
    @NotBlank(message = "Category name cannot be empty")
    private String categoryName;
    private String imageUrl;
}
