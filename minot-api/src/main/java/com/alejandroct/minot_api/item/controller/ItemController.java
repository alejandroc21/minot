package com.alejandroct.minot_api.item.controller;

import com.alejandroct.minot_api.item.dto.ItemDTO;
import com.alejandroct.minot_api.item.dto.ItemFilter;
import com.alejandroct.minot_api.item.mapper.ItemMapper;
import com.alejandroct.minot_api.item.model.Item;
import com.alejandroct.minot_api.item.repository.ItemRepository;
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
    public ResponseEntity<Page<ItemDTO>> list(Pageable pageable){
        return ResponseEntity.ok(this.iItemService.list(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ItemDTO>> filterList(ItemFilter itemFilter, Pageable pageable, Principal principal){
        return ResponseEntity.ok(this.iItemService.filterList(itemFilter,pageable, principal.getName()));
    }

}
