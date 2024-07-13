package com.tosan.validation.dto;

import com.tosan.validation.constraints.Iban;

/**
 * @author Habib Motallebpour
 * @since 16/07/2016
 */
public class IbanTestDto {

    @Iban
    private String iban;

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getIban() {
        return iban;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("MyTestDto");
        sb.append("{iban=").append(iban);
        sb.append('}');
        return sb.toString();
    }
}
