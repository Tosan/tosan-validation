package com.tosan.validation.dto;

import com.tosan.validation.constraints.Email;
import com.tosan.validation.constraints.Length;
import com.tosan.validation.order.First;
import com.tosan.validation.order.Second;
import com.tosan.validation.order.Third;
import jakarta.validation.constraints.NotEmpty;

/**
 * @author Habib Motallebpour
 * @since 26/10/2016
 */
public class GroupSequenceDto {

    @Email(groups = Second.class)
    @NotEmpty(groups = First.class)
    @Length(max = 20, groups = Third.class)
    private String str;

    public void setStr(String str) {
        this.str = str;
    }
}
