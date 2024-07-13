package com.tosan.validation.dto;

import com.tosan.validation.constraints.Pervasive;

/**
 * @author R.Mehri
 * @since 1/9/2022
 */
public class PervasiveTestDto {

    @Pervasive
    private String pervasive;

    public void setPervasive(String pervasive) {
        this.pervasive = pervasive;
    }

    public String getPervasive() {
        return pervasive;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("MyTestDto");
        sb.append("{pervasive=").append(pervasive);
        sb.append('}');
        return sb.toString();
    }
}
