package com.alejandroct.minot_api.category.service.impl;

import com.alejandroct.minot_api.category.dto.CategoryDTO;
import com.alejandroct.minot_api.category.mapper.CategoryMapper;
import com.alejandroct.minot_api.category.model.Category;
import com.alejandroct.minot_api.category.repository.CategoryRepository;
import com.alejandroct.minot_api.category.service.CategoryService;
import com.alejandroct.minot_api.user.model.User;
import com.alejandroct.minot_api.user.service.IUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final IUserService userService;

    @Override
    public Page<CategoryDTO> list(Pageable pageable, String email) {
        Page<Category> categories = this.categoryRepository.findByUserEmail(email, pageable);
        return categories.map(this.categoryMapper::toDto);
    }

    @Override
    public Category findByIdAndUserEmail(Long id, String email){
        return this.categoryRepository.findByIdAndUserEmail(id, email)
                .orElseThrow(() -> new EntityNotFoundException("Category not exists"));
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO, String email) {
        User user = this.userService.findUserByEmail(email);
        Category category = this.categoryMapper.toEntity(categoryDTO);
        category.setId(null);
        category.setUser(user);
        return this.categoryMapper.toDto(this.categoryRepository.save(category));
    }

    @Override
    public CategoryDTO update(Long id, CategoryDTO categoryDTO, String email) {
        Category category = this.findByIdAndUserEmail(id, email);
        category.setName(categoryDTO.name());
        category.setColor(categoryDTO.color());
        return this.categoryMapper.toDto(this.categoryRepository.save(category));
    }

    @Override
    public Boolean delete(Long id, String email) {
        this.findByIdAndUserEmail(id, email);
        this.categoryRepository.deleteById(id);
        return true;
    }
}
