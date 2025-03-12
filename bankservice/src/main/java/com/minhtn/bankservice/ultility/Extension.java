package com.minhtn.bankservice.ultility;

import lombok.experimental.UtilityClass;

import java.util.Collection;

@UtilityClass
public class Extension {
    public static boolean isBlankOrNull(String str) {
        return str == null || str.isEmpty();
    }

    public static <T> boolean isNullOrEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }
}
