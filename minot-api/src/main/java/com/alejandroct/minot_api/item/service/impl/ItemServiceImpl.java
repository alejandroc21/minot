package com.alejandroct.minot_api.item.service.impl;

import com.alejandroct.minot_api.folder.model.Folder;
import com.alejandroct.minot_api.item.dto.ItemDTO;
import com.alejandroct.minot_api.item.dto.ItemFilter;
import com.alejandroct.minot_api.item.filter.ItemSpecification;
import com.alejandroct.minot_api.item.mapper.ItemMapper;
import com.alejandroct.minot_api.item.model.Item;
import com.alejandroct.minot_api.item.repository.ItemRepository;
import com.alejandroct.minot_api.item.service.IItemService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements IItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    private Item findItemByIdAndUserEmail(Long id, String email){
        return this.itemRepository.findByIdAndUserEmail(id, email)
                .orElseThrow(()->new EntityNotFoundException("item not found"));
    }

    @Override
    public Page<ItemDTO> filterList(ItemFilter itemFilter, Pageable pageable, String email) {
//       Specification<Item> spec = ItemSpecification.withFilters(itemFilter).and(ItemSpecification.byUserEmail(email));
        Specification<Item> spec = ItemSpecification.withFilters(itemFilter, email);
        Page<Item> items = this.itemRepository.findAll(spec, pageable);
       return items.map(this.itemMapper::toDto);
    }

    @Override
    @Transactional
    public Boolean sendToTrash(Long id, String email) {
        Item item = this.findItemByIdAndUserEmail(id, email);
        item.setTrashed(true);
        List<Item> items = new ArrayList<>();
        this.collectItemsToTrash(item, items);
        items.forEach(i -> i.setTrashed(true));
        this.itemRepository.saveAll(items);
        return true;
    }

    private void collectItemsToTrash(Item item, List<Item> items){
        items.add(item);
        if(item instanceof Folder folder){
            folder.getChildren().forEach(note -> collectItemsToTrash(note, items));
            folder.getChildren().forEach(child -> collectItemsToTrash(child, items));
        }
    }

    @Override
    public Boolean delete(Long id, String email) {
        this.findItemByIdAndUserEmail(id, email);
        this.itemRepository.deleteById(id);
        return true;
    }
}
