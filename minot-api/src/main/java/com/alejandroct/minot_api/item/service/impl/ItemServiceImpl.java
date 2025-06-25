package com.alejandroct.minot_api.item.service.impl;

import com.alejandroct.minot_api.item.dto.ItemDTO;
import com.alejandroct.minot_api.item.dto.ItemFilter;
import com.alejandroct.minot_api.item.filter.ItemSpecification;
import com.alejandroct.minot_api.item.mapper.ItemMapper;
import com.alejandroct.minot_api.item.model.Item;
import com.alejandroct.minot_api.item.repository.ItemRepository;
import com.alejandroct.minot_api.item.service.IItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements IItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Override
    public Page<ItemDTO> list(Pageable pageable) {
        Page<Item> items = this.itemRepository.findAll(pageable);
        return items.map(this.itemMapper::toDto);
    }

    @Override
    public Page<ItemDTO> filterList(ItemFilter itemFilter, Pageable pageable, String email) {
//       Specification<Item> spec = ItemSpecification.withFilters(itemFilter).and(ItemSpecification.byUserEmail(email));
        Specification<Item> spec = ItemSpecification.withFilters(itemFilter, email);
        Page<Item> items = this.itemRepository.findAll(spec, pageable);
       return items.map(this.itemMapper::toDto);
    }
}
