package com.tosan.validation;

import com.tosan.validation.dto.NationalCodeDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author M.Hoseini
 * @since 6/26/2023
 */
public class NationalCodeTest {

    @Test
    public void validate_normalNationalCode() {
        NationalCodeDto nationalCodeDto = new NationalCodeDto();
        nationalCodeDto.setNationalCode("0015858812");
        ValidatorTest.validate(new Object[]{nationalCodeDto});
    }

    @Test
    public void validate_invalidNationalCode_exceptionThrown() {
        NationalCodeDto nationalCodeDto = new NationalCodeDto();
        nationalCodeDto.setNationalCode("0015858818");
        assertThrows(RuntimeException.class, () -> ValidatorTest.validate(new Object[]{nationalCodeDto}));
    }

    @Test
    public void validate_invalidNumberOfDigits_exceptionThrown() {
        NationalCodeDto nationalCodeDto = new NationalCodeDto();
        nationalCodeDto.setNationalCode("00158588123");
        assertThrows(RuntimeException.class, () -> ValidatorTest.validate(new Object[]{nationalCodeDto}));
    }

    @Test
    public void validate_invalidInput_exceptionThrown() {
        NationalCodeDto nationalCodeDto = new NationalCodeDto();
        nationalCodeDto.setNationalCode("ششش1585881j");
        assertThrows(RuntimeException.class, () -> ValidatorTest.validate(new Object[]{nationalCodeDto}));
    }
}
