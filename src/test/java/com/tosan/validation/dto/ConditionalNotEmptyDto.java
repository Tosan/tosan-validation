package com.tosan.validation.dto;

import com.tosan.validation.constraints.ConditionalNotEmpty;

/**
 * @author a.ebrahimi
 * @since 5/28/2024
 */
@ConditionalNotEmpty(selected = "target", condition = "condition")
public class ConditionalNotEmptyDto {

    private Boolean condition;

    private Long target;

    public Boolean getCondition() {
        return condition;
    }

    public void setCondition(Boolean condition) {
        this.condition = condition;
    }

    public Long getTarget() {
        return target;
    }

    public void setTarget(Long target) {
        this.target = target;
    }
}
