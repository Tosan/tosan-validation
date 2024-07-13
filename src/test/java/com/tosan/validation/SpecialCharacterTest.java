package com.tosan.validation;

import com.tosan.validation.dto.SpecialCharacterDto;
import org.testng.annotations.Test;

/**
 * @author P.khoshkhou
 * @since 09/04/2023
 */
public class SpecialCharacterTest {

    @Test
    public void validate_normalStringWithoutSpecialCharacter() {
        SpecialCharacterDto dto = new SpecialCharacterDto();
        dto.setSpecialCharacter("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789اب پ ت ث ج چ ح خ د ذ ر ز ص ض س ش ع غ ف ق ک گ ل ن و ه ی");
        ValidatorTest.validate(new Object[]{dto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void validate_invalidStringWithSpecialCharacter_exceptionThrown() {
        SpecialCharacterDto dto = new SpecialCharacterDto();
        dto.setSpecialCharacter("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789^^<>%$@#&*()!{}':;`");
        ValidatorTest.validate(new Object[]{dto});
    }
}
