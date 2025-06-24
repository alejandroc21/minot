package com.alejandroct.minot_api.item.controller;

import com.alejandroct.minot_api.item.dto.ItemDTO;
import com.alejandroct.minot_api.item.mapper.ItemMapper;
import com.alejandroct.minot_api.item.model.Item;
import com.alejandroct.minot_api.item.repository.ItemRepository;
import com.alejandroct.minot_api.item.service.IItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @GetMapping
    public ResponseEntity<Page<ItemDTO>> list(Pageable pageable){
        Page<Item> items = this.itemRepository.findAll(pageable);
        Page<ItemDTO> itemDTOS = items.map(this.itemMapper::toDto);
        return ResponseEntity.ok(itemDTOS);
    }

}
