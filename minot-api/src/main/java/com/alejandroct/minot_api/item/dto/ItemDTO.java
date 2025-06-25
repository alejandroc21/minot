package com.alejandroct.minot_api.item.dto;

import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

public interface ItemDTO{
        Long getId();
        String getName();
        boolean isTrashed();
        String getType();
}

