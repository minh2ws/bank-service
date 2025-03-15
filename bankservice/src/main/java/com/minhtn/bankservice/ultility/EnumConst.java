package com.minhtn.bankservice.ultility;

public class EnumConst {
    public enum Currency {
        VND, USD, EUR, JPY, CNY;

        public static Currency fromString(String currency) {
            for (Currency c : Currency.values()) {
                if (c.name().equalsIgnoreCase(currency)) {
                    return c;
                }
            }
            return null;
        }
    }
}
