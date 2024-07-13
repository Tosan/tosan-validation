package com.tosan.validation.dto;

import com.tosan.validation.constraints.DateDifference;
import com.tosan.validation.constraints.DateDifferences;

import java.util.Date;

/**
 * @author Ali Alimohammadi
 * @since 21/06/2020
 */
@DateDifferences(value = {
        @DateDifference(fromFieldName = "fromDate", toFieldName = "toDate", maxDifference = 10L, minDifference = 3L)
})
public class DateDifferenceDto {
    private Date fromDate;
    private Date toDate;

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}
