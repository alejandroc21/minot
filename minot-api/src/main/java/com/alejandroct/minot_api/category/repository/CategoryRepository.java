package com.alejandroct.minot_api.category.repository;

import com.alejandroct.minot_api.category.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findByUserEmail(String email, Pageable pageable);

    Optional<Category> findByIdAndUserEmail(Long id, String email);
}
