package com.exponab.invoice.constants;

import java.math.BigDecimal;

public class QuotationConstants {

    public static final String DEFAULT_TERMS_AND_CONDITIONS =
            "1. This quotation is valid for 30 days from the date of issue.\n" +
            "2. Payment is due within 30 days of invoice date.\n" +
            "3. All services are subject to RARA company terms of service.\n" +
            "4. RARA reserves the right to modify services upon mutual agreement.\n" +
            "5. Any additional requirements beyond the scope will be quoted separately.";

    public static final String DEFAULT_NOTES =
            "Thank you for considering RARA for your counselling needs. " +
            "Please feel free to reach out for any clarifications.";

    public static final String DEFAULT_CURRENCY = "INR";

    public static final int QUOTATION_VALIDITY_DAYS = 30;

    public static final BigDecimal DEFAULT_TAX_PERCENT = new BigDecimal("18.0"); // GST
    public static final BigDecimal DEFAULT_DISCOUNT_PERCENT = BigDecimal.ZERO;

    private QuotationConstants() {}
}
