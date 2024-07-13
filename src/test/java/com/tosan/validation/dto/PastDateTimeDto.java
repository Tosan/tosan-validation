package com.tosan.validation.dto;

import com.tosan.validation.constraints.PastDateTime;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author Marjan Mehranfar
 * @since 13/07/2019
 */
public class PastDateTimeDto {

    @PastDateTime
    Date date;

    @PastDateTime
    ZonedDateTime zonedDate;

    public void setDate(Date date) {
        this.date = date;
    }

    public void setZonedDate(ZonedDateTime zonedDate) {
        this.zonedDate = zonedDate;
    }
}
