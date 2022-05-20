package com;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CurrencyManipulatorFactory {
    private static final Map<String, CurrencyManipulator> map = new HashMap<>();

    private CurrencyManipulatorFactory() {
    }

    public static CurrencyManipulator getManipulatorByCurrencyCode(String currencyCode) {
        if (currencyCode != null && !currencyCode.isEmpty()) {
            String curerncyCodeRight = currencyCode.toUpperCase();
            CurrencyManipulator manipulator;
            if (!map.containsKey(curerncyCodeRight)) {
                manipulator = map.put(curerncyCodeRight, new CurrencyManipulator(curerncyCodeRight));
            } else
            manipulator = map.get(curerncyCodeRight);
            return manipulator;
        }

        return null;
    }

    public static Collection<CurrencyManipulator> getAllCurrencyManipulators(){
        return new ArrayList<>(map.values());
    }
}
