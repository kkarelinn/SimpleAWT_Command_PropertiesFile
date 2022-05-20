package com;

import com.exception.NotEnoughMoneyException;

import java.util.*;

public class CurrencyManipulator {

    private final String currencyCode;
    private Map<Integer, Integer> denominations;


    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
        denominations = new HashMap<>();
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void addAmount(int denomination, int count) {
        if (denomination > 0 && count > 0) {
            if (denominations.containsKey(denomination)) {
                int pcs = denominations.get(denomination) + count;
                denominations.replace(denomination, pcs);
            } else denominations.put(denomination, count);
        }
    }

    public int getTotalAmount(){
        return denominations.entrySet().stream().mapToInt(e->e.getKey()*e.getValue()).sum();
    }

    public boolean hasMoney() {
        return !denominations.isEmpty();
    }

    public  boolean isAmountAvailable(int expectedAmount){
       return expectedAmount <= getTotalAmount();
    }



    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {
        int sum = expectedAmount;
        HashMap<Integer, Integer> copyDenominations = new HashMap<>(denominations);

        ArrayList<Integer> keys = new ArrayList<>(this.denominations.keySet());

        Collections.sort(keys);
        Collections.reverse(keys);

        TreeMap<Integer, Integer> resultMap = new TreeMap<>(Comparator.reverseOrder());

        for (Integer denomination : keys) {
            final int key = denomination;
            int value = copyDenominations.get(key);
            while (true) {
                if (sum < key || value == 0) {
                    copyDenominations.put(key, value);
                    break;
                }
                sum -= key;
                value--;

                if (resultMap.containsKey(key))
                    resultMap.put(key, resultMap.get(key) + 1);
                else
                    resultMap.put(key, 1);
            }
        }

        if (sum > 0)
            throw new NotEnoughMoneyException();
        else {
            this.denominations.clear();
            this.denominations.putAll(copyDenominations);
        }
        return resultMap;
    }//;, который вернет карту HashMap<номинал, количество>.
}
