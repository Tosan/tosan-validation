package com.tosan.validation;

import com.tosan.validation.dto.PervasiveTestDto;
import org.testng.annotations.Test;

/**
 * @author R.Mehri
 * @since 1/9/2022
 */
public class PervasiveTest {

    @Test
    public void pervasiveTt() {
        PervasiveTestDto pervasiveTestDto = new PervasiveTestDto();
        pervasiveTestDto.setPervasive("53165395");
        ValidatorTest.validate(new Object[]{pervasiveTestDto});
    }
}
