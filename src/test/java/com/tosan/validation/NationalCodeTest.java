package com.tosan.validation;

import com.tosan.validation.dto.NationalCodeDto;
import org.testng.annotations.Test;

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

    @Test(expectedExceptions = RuntimeException.class)
    public void validate_invalidNationalCode_exceptionThrown() {
        NationalCodeDto nationalCodeDto = new NationalCodeDto();
        nationalCodeDto.setNationalCode("0015858818");
        ValidatorTest.validate(new Object[]{nationalCodeDto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void validate_invalidNumberOfDigits_exceptionThrown() {
        NationalCodeDto nationalCodeDto = new NationalCodeDto();
        nationalCodeDto.setNationalCode("00158588123");
        ValidatorTest.validate(new Object[]{nationalCodeDto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void validate_invalidInput_exceptionThrown() {
        NationalCodeDto nationalCodeDto = new NationalCodeDto();
        nationalCodeDto.setNationalCode("ششش1585881j");
        ValidatorTest.validate(new Object[]{nationalCodeDto});
    }
}
