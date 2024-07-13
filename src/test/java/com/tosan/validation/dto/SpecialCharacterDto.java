package com.tosan.validation.dto;

import com.tosan.validation.constraints.SpecialCharacter;

/**
 * @author P.khoshkhou
 * @since 09/04/2023
 */
public class SpecialCharacterDto {

    @SpecialCharacter
    private String specialCharacter;

    public void setSpecialCharacter(String specialCharacter) {
        this.specialCharacter = specialCharacter;
    }

    public @SpecialCharacter String getSpecialCharacter() {
        return specialCharacter;
    }
}
