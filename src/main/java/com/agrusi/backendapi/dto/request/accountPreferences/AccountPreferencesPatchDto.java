package com.agrusi.backendapi.dto.request.accountPreferences;

import com.agrusi.backendapi.validator.annotation.ValidCurrencyCode;
import com.agrusi.backendapi.validator.annotation.ValidLanguageCode;
import com.agrusi.backendapi.validator.annotation.ValidFieldAreaUnit;
import com.agrusi.backendapi.validator.annotation.ValidTimeZone;

public class AccountPreferencesPatchDto {

    @ValidLanguageCode
    private String language; // e.g., "fi", "en-us"

    @ValidCurrencyCode
    private String currency; // e.g., "EUR", "USD"

    @ValidTimeZone
    private String timeZone; // e.g., "Europe/Helsinki", "America/New_York"

    @ValidFieldAreaUnit
    private String fieldAreaUnit; // e.g., "metric", "imperial"

    public AccountPreferencesPatchDto() {
    }

    public AccountPreferencesPatchDto(
            String language,
            String currency,
            String timeZone,
            String fieldAreaUnit
    ) {
        this.language = language;
        this.currency = currency;
        this.timeZone = timeZone;
        this.fieldAreaUnit = fieldAreaUnit;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getFieldAreaUnit() {
        return fieldAreaUnit;
    }

    public void setFieldAreaUnit(String fieldAreaUnit) {
        this.fieldAreaUnit = fieldAreaUnit;
    }
}
