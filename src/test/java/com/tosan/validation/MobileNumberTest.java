package com.tosan.validation;

import com.tosan.validation.dto.MobileNumberDto;
import org.testng.annotations.Test;

/**
 * @author a.ebrahimi
 * @since 10/24/2023
 */
public class MobileNumberTest {

    @Test
    public void validate_validMobileNumberWithZero() {
        MobileNumberDto mobileNumberDto = new MobileNumberDto();
        mobileNumberDto.setMobileNumber("09124229804");
        ValidatorTest.validate(new Object[]{mobileNumberDto});
    }

    @Test
    public void validate_validMobileNumberWithNull() {
        MobileNumberDto mobileNumberDto = new MobileNumberDto();
        ValidatorTest.validate(new Object[]{mobileNumberDto});
    }

    @Test
    public void validate_validMobileNumberWithPlus() {
        MobileNumberDto mobileNumberDto = new MobileNumberDto();
        mobileNumberDto.setMobileNumber("+989124229804");
        ValidatorTest.validate(new Object[]{mobileNumberDto});
    }

    @Test
    public void validate_validNonIranMobileNumberWithPlus() {
        MobileNumberDto mobileNumberDto = new MobileNumberDto();
        mobileNumberDto.setMobileNumber("+959124229804");
        ValidatorTest.validate(new Object[]{mobileNumberDto});
    }

    @Test
    public void validate_validMobileNumberMobileNumberWithTwoZeros() {
        MobileNumberDto mobileNumberDto = new MobileNumberDto();
        mobileNumberDto.setMobileNumber("00989124229804");
        ValidatorTest.validate(new Object[]{mobileNumberDto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void validate_longValue_exceptionThrown() {
        MobileNumberDto mobileNumberDto = new MobileNumberDto();
        mobileNumberDto.setMobileNumber("091242254851452152152154");
        ValidatorTest.validate(new Object[]{mobileNumberDto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void validate_longValueWithPlus_exceptionThrown() {
        MobileNumberDto mobileNumberDto = new MobileNumberDto();
        mobileNumberDto.setMobileNumber("+9891242254851452152152154");
        ValidatorTest.validate(new Object[]{mobileNumberDto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void validate_invalidMobile_exceptionThrown() {
        MobileNumberDto mobileNumberDto = new MobileNumberDto();
        mobileNumberDto.setMobileNumber("not a mobile number");
        ValidatorTest.validate(new Object[]{mobileNumberDto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void validate_invalidMobileWithNumberAndChar_exceptionThrown() {
        MobileNumberDto mobileNumberDto = new MobileNumberDto();
        mobileNumberDto.setMobileNumber("0912fff9804");
        ValidatorTest.validate(new Object[]{mobileNumberDto});
    }
}
