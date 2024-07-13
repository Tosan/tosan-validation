package com.tosan.validation;

import com.tosan.validation.dto.GroupSequenceDto;
import com.tosan.validation.order.CheckOrder;
import org.testng.annotations.Test;

/**
 * @author Habib Motallebpour
 * @since 26/10/2016
 */
public class GroupSequenceTest {

    @Test
    public void groupSeqTest() {
        GroupSequenceDto groupSequenceDto = new GroupSequenceDto();
        groupSequenceDto.setStr("a@a.com");
        ValidatorTest.validate(new Object[]{groupSequenceDto}, CheckOrder.class);
    }
}
