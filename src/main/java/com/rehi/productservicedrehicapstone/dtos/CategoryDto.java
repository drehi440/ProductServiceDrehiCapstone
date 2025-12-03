package com.rehi.productservicedrehicapstone.dtos;

import com.rehi.productservicedrehicapstone.models.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private Long categoryId;
    private String categoryName;

    public static CategoryDto from(Category category) {
        if (category == null) {
            return null;
        }
        return CategoryDto.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .build();
    }
}


