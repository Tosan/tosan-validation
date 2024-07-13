package com.tosan.validation.constraints;

/**
 * جهت validate کردن مقادیر فیلدهای تاریخ استفاده می شود و شامل مقادیر زیر است :
 * FROM_TODAY = مقدار فیلد تاریخ  می تواند برابر تاریخ جاری باشد یا اینکه  تاریخی در آینده باشد.
 * FROM_TOMORROW = مقدار فیلد تاریخ باید تاریخی در آینده باشد و نمی تواند تاریخ جاری باشد.
 *
 * @author shamsolebad
 */
public enum DurationValidator {
    FROM_TODAY,
    FROM_TOMORROW,
    FROM_TODAY_TIME,
    FROM_TOMORROW_TIME
}