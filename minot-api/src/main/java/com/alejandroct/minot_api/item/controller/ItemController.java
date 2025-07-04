package com.alejandroct.minot_api.item.controller;

import com.alejandroct.minot_api.item.dto.ItemDTO;
import com.alejandroct.minot_api.item.dto.ItemFilter;
import com.alejandroct.minot_api.item.dto.ItemType;
import com.alejandroct.minot_api.item.service.IItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {
    private final IItemService iItemService;

    @GetMapping
    public ResponseEntity<Page<ItemDTO>> filterList(
            @RequestParam(name = "type", required = false) List<ItemType> type,
            @RequestParam(name = "text", required = false) String text,
            @RequestParam(name = "trashed", required = false) Boolean trashed,
            Pageable pageable,
            Principal principal) {

        ItemFilter itemFilter = new ItemFilter(type, text, trashed);
        return ResponseEntity.ok(this.iItemService.filterList(itemFilter,pageable, principal.getName()));
    }

    @PostMapping("/trash/{id}")
    public ResponseEntity<ItemDTO> sendToTrash(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(this.iItemService.sendToTrash(id, principal.getName()));
    }

    @PostMapping("/restore/{id}")
    public ResponseEntity<ItemDTO> restore(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(this.iItemService.restore(id, principal.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(this.iItemService.delete(id, principal.getName()));
    }

}
