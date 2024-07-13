package com.tosan.validation.dto;

import com.tosan.validation.constraints.UUID;

/**
 * @author P.khoshkhou
 * @since 09/04/2023
 */
public class UUIDDto {

    @UUID
    String uuid;

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
