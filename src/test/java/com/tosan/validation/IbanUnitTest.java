package com.tosan.validation;

import com.tosan.validation.dto.IbanTestDto;
import org.testng.annotations.Test;

/**
 * @author Habib Motallebpour
 * @since 26/10/2016
 */
public class IbanUnitTest {

    private final IbanTestDto ibanTestDto = new IbanTestDto();

    @Test
    public void nullIBanTest() {
        ibanTestDto.setIban(null);
        ValidatorTest.validate(new Object[]{ibanTestDto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void invalidLengthTest() {
        ibanTestDto.setIban("IR062960000000100324200001 ");
        ValidatorTest.validate(new Object[]{ibanTestDto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void invalidPrefixTest() {
        ibanTestDto.setIban("US062960000000100324200001 ");
        ValidatorTest.validate(new Object[]{ibanTestDto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void digitTest() {
        ibanTestDto.setIban("US0629600000SINA0324200001 ");
        ValidatorTest.validate(new Object[]{ibanTestDto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void invalidSysTypeTest() {
        ibanTestDto.setIban("IR062965000000100324200001");
        ValidatorTest.validate(new Object[]{ibanTestDto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void invalidIbanTest() {
        ibanTestDto.setIban("IR062960000000100324200009");
        ValidatorTest.validate(new Object[]{ibanTestDto});
    }

    @Test()
    public void validIbanTest() {
        ibanTestDto.setIban("IR062960000000100324200001");
        ValidatorTest.validate(new Object[]{ibanTestDto});
    }
}
