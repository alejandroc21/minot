package com.alejandroct.minot_api.category.mapper;

import com.alejandroct.minot_api.category.dto.CategoryDTO;
import com.alejandroct.minot_api.category.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO toDto(Category category);

    Category toEntity(CategoryDTO categoryDTO);
}
