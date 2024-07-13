package com.tosan.validation.dto;

import com.tosan.validation.constraints.FutureDate;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author Habib Motallebpour
 * @since 30/10/2016
 */
public class FutureDateDto {

    @FutureDate
    private Date date;

    @FutureDate
    private ZonedDateTime zonedDate;

    @FutureDate
    private LocalDate localDate;

    public void setDate(Date date) {
        this.date = date;
    }

    public void setZonedDate(ZonedDateTime zonedDate) {
        this.zonedDate = zonedDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
}
