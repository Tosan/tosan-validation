package com.tosan.validation.dto;

import com.tosan.validation.constraints.NationalCode;

/**
 * @author M.Hoseini
 * @since 6/26/2023
 */

public class NationalCodeDto {

    @NationalCode
    String nationalCode;

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }
}
