package com.alejandroct.minot_api.item.dto;

public interface ItemDTO{
        Long getId();
        String getName();
        String getContent();
        boolean isTrashed();
        String getType();
}

