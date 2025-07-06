package com.alejandroct.minot_api.category.service;

import com.alejandroct.minot_api.category.dto.CategoryDTO;
import com.alejandroct.minot_api.category.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<CategoryDTO> list(Pageable pageable, String email);
    Category findByIdAndUserEmail(Long id, String email);

    CategoryDTO save(CategoryDTO categoryDTO, String email);

    CategoryDTO update(Long id, CategoryDTO categoryDTO, String email);

    Boolean delete(Long id, String email);
}
