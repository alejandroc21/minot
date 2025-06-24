package com.alejandroct.minot_api.item.service.impl;

import com.alejandroct.minot_api.item.repository.ItemRepository;
import com.alejandroct.minot_api.item.service.IItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements IItemService {
    private final ItemRepository itemRepository;
}
