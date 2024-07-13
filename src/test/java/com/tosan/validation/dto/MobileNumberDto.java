package com.tosan.validation.dto;

import com.tosan.validation.constraints.MobileNumber;

/**
 * @author a.ebrahimi
 * @since 10/24/2023
 */
public class MobileNumberDto {

    @MobileNumber
    private String mobileNumber;

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
