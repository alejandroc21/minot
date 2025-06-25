package com.alejandroct.minot_api.item.service;

import com.alejandroct.minot_api.item.dto.ItemDTO;
import com.alejandroct.minot_api.item.dto.ItemFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IItemService {
    Page<ItemDTO> list(Pageable pageable);

    Page<ItemDTO> filterList(ItemFilter itemFilter, Pageable pageable, String email);
}
