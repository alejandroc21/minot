package com.alejandroct.minot_api.item.dto;

import java.util.List;

public record ItemFilter(
        List<ItemType> types,
        String text
) {
    public enum ItemType{
        FOLDER, NOTE
    }
}
