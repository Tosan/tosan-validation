package com.tosan.validation.dto;

import com.tosan.validation.constraints.ValidCollection;

import java.util.List;

/**
 * @author Ali Alimohammadi
 * @since 21/06/2020
 */
public class DateDifferenceListDto {

    @ValidCollection(elementType = DateDifferenceDto.class)
    private List<DateDifferenceDto> dateList;

    public List<DateDifferenceDto> getDateList() {
        return dateList;
    }

    public void setDateList(List<DateDifferenceDto> dateList) {
        this.dateList = dateList;
    }
}
