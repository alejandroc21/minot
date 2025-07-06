package com.alejandroct.minot_api.category.controller;

import com.alejandroct.minot_api.category.dto.CategoryDTO;
import com.alejandroct.minot_api.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> list(Pageable pageable, Principal principal){
        return ResponseEntity.ok(this.categoryService.list(pageable, principal.getName()));

    }

    @PostMapping
    public ResponseEntity<CategoryDTO> save(@RequestBody CategoryDTO categoryDTO, Principal principal){
        return new ResponseEntity<>(this.categoryService.save(categoryDTO, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO, Principal principal){
        return ResponseEntity.ok(this.categoryService.update(id, categoryDTO, principal.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(this.categoryService.delete(id, principal.getName()));
    }
}
