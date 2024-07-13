package com.tosan.validation.dto;

import com.tosan.tools.jalali.JalaliDate;
import com.tosan.validation.constraints.DurationValidator;
import com.tosan.validation.constraints.FutureJalaliDate;
import com.tosan.validation.constraints.ValidateJalaliDate;

/**
 * @author Mostafa Abdollahi
 * @since 3/17/13
 */
public class JalaliDateDto {

    @ValidateJalaliDate
    private JalaliDate past;

    @FutureJalaliDate(type = DurationValidator.FROM_TODAY)
    private JalaliDate now;

    @FutureJalaliDate(type = DurationValidator.FROM_TOMORROW)
    private JalaliDate future;

    public JalaliDate getFuture() {
        return future;
    }

    public void setFuture(JalaliDate future) {
        this.future = future;
    }

    public JalaliDate getNow() {
        return now;
    }

    public void setNow(JalaliDate now) {
        this.now = now;
    }

    public JalaliDate getPast() {
        return past;
    }

    public void setPast(JalaliDate past) {
        this.past = past;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("JalaliDateDto");
        sb.append("{future=").append(future);
        sb.append(", past=").append(past);
        sb.append(", now=").append(now);
        sb.append('}');
        return sb.toString();
    }
}
