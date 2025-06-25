package com.alejandroct.minot_api.item.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ItemFilter(
        List<ItemType> type,
        String text,
        Boolean trashed
) {
}
