package com.gildedrose;

@FunctionalInterface
public interface QualityCalculatorInterface {

    int MAX = 50;
    int MIN = 0;


    int changeQuality(Item item);

    static int rangeEnsure(int quality) {
        return Math.min(MAX, Math.max(MIN, quality));
    }

    static int calculate(int a, int b) {
        return  rangeEnsure(a - b);
    }

}
