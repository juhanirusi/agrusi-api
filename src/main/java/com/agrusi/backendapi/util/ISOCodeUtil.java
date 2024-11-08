package com.agrusi.backendapi.util;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ISOCodeUtil {

    // List of valid ISO 639-1 language codes
    public static final Set<String> ISO_LANGUAGE_CODES = Set.of("en-us", "fi", "sv");

    // List of valid ISO 4217 currency codes
    public static final Set<String> ISO_CURRENCY_CODES = Set.of("USD", "EUR", "SEK");

    public static boolean isValidLanguageCode(String code) {
        return ISO_LANGUAGE_CODES.contains(code.toLowerCase());
    }

    public static boolean isValidCurrencyCode(String code) {
        return ISO_CURRENCY_CODES.contains(code.toUpperCase());
    }
}
