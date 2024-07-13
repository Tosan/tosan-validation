package com.tosan.validation.dto;

import com.tosan.validation.constraints.PastDate;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author Habib Motallebpour
 * @since 30/10/2016
 */
public class PastDateDto {

    @PastDate
    Date date;

    @PastDate
    ZonedDateTime zonedDate;

    public void setDate(Date date) {
        this.date = date;
    }

    public void setZonedDate(ZonedDateTime zonedDate) {
        this.zonedDate = zonedDate;
    }

}
