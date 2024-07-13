package com.tosan.validation;

import com.tosan.validation.dto.UUIDDto;
import org.testng.annotations.Test;

import java.util.UUID;

/**
 * @author P.khoshkhou
 * @since 09/04/2023
 */
public class UUIDTest {

    @Test
    public void validate_normalUUID() {
        UUIDDto uuidDto = new UUIDDto();
        uuidDto.setUuid(UUID.randomUUID().toString());
        ValidatorTest.validate(new Object[]{uuidDto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void validate_invalidUUID_exceptionThrown() {
        UUIDDto uuidDto = new UUIDDto();
        uuidDto.setUuid("not uuid");
        ValidatorTest.validate(new Object[]{uuidDto});
    }
}
