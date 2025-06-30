package com.alejandroct.minot_api.item.service;

import com.alejandroct.minot_api.item.dto.ItemDTO;
import com.alejandroct.minot_api.item.dto.ItemFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IItemService {

    Page<ItemDTO> filterList(ItemFilter itemFilter, Pageable pageable, String email);

    Boolean sendToTrash(Long id, String email);

    Boolean delete(Long id, String email);
}
