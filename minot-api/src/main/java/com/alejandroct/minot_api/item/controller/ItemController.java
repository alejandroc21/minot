package com.alejandroct.minot_api.item.controller;

import com.alejandroct.minot_api.item.dto.ItemDTO;
import com.alejandroct.minot_api.item.dto.ItemFilter;
import com.alejandroct.minot_api.item.service.IItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {
    private final IItemService iItemService;

    @GetMapping
    public ResponseEntity<Page<ItemDTO>> filterList(ItemFilter itemFilter, Pageable pageable, Principal principal){
        return ResponseEntity.ok(this.iItemService.filterList(itemFilter,pageable, principal.getName()));
    }

    @PostMapping("/trash/{id}")
    public ResponseEntity<Boolean> sendToTrash(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(this.iItemService.sendToTrash(id, principal.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(this.iItemService.delete(id, principal.getName()));
    }

}
