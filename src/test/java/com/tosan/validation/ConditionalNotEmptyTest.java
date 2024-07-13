package com.tosan.validation;

import com.tosan.validation.dto.ConditionalNotEmptyDto;
import org.testng.annotations.Test;

/**
 * @author a.ebrahimi
 * @since 5/28/2024
 */
public class ConditionalNotEmptyTest {

    @Test
    public void validate_conditionIsFalseAndTargetFieldIsNull_validationSuccessFull() {
        ConditionalNotEmptyDto conditionalNotEmptyDto = new ConditionalNotEmptyDto();
        conditionalNotEmptyDto.setCondition(false);
        ValidatorTest.validate(new Object[]{conditionalNotEmptyDto});
    }

    @Test
    public void validate_conditionIsFalseAndTargetFieldIsNotNull_validationSuccessFull() {
        ConditionalNotEmptyDto conditionalNotEmptyDto = new ConditionalNotEmptyDto();
        conditionalNotEmptyDto.setCondition(false);
        conditionalNotEmptyDto.setTarget(1L);
        ValidatorTest.validate(new Object[]{conditionalNotEmptyDto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void validate_conditionIsTrueAndTargetFieldIsNull_validationNotSuccessFull() {
        ConditionalNotEmptyDto conditionalNotEmptyDto = new ConditionalNotEmptyDto();
        conditionalNotEmptyDto.setCondition(true);
        ValidatorTest.validate(new Object[]{conditionalNotEmptyDto});
    }

    @Test
    public void validate_conditionIsTrueAndTargetFieldIsNotNull_validationSuccessFull() {
        ConditionalNotEmptyDto conditionalNotEmptyDto = new ConditionalNotEmptyDto();
        conditionalNotEmptyDto.setCondition(true);
        conditionalNotEmptyDto.setTarget(1L);
        ValidatorTest.validate(new Object[]{conditionalNotEmptyDto});
    }
}
