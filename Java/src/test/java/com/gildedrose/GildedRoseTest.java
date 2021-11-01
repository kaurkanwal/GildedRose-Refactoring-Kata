package com.gildedrose;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GildedRoseTest {

    private static Item[] items;


    @BeforeAll()
    public static void setup() {
        items = new Item[]{
            new Item("+5 Dexterity Vest", 10, 20), //
            new Item("Aged Brie", 2, 0), //
            new Item("Aged Brie", 0, 49), //
            new Item("Elixir of the Mongoose", 5, 7), //
            new Item("Sulfuras, Hand of Ragnaros", 0, 80), //
            new Item("Sulfuras, Hand of Ragnaros", -1, 80),
            new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
            // this conjured item does not work properly yet
            new Item("Conjured Mana Cake", 3, 6)};
    }

    @Test
    void onceSellDatePassedQualityDegradesTwiceTest() {
        //given
        Item[] itemSellDatePassed = Arrays.stream(items)
            .map( s -> new Item(s.name, 0, s.quality))
            .toArray( Item[]::new);
        GildedRose app = new GildedRose(itemSellDatePassed);

        //when
        app.updateQuality();

        //then
        //quality improves twice
        assertEquals(2, app.items[1].quality);
        assertEquals(18, app.items[0].quality);
        assertEquals(50, app.items[2].quality, "Quality cannot be over 50");
        assertEquals(5, app.items[3].quality);



    }

    @Test
    void onceSellDatePassedBackStagePassesDropToZero() {
        //given
        Item[] itemSellDatePassed = {new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20)};
        GildedRose app = new GildedRose(itemSellDatePassed);

        //when
        app.updateQuality();

        //quality decreases to 0
        assertEquals(0, app.items[0].quality);
    }

    @Test
    void qualityofItemNeverNegative(){
        //given
        Item[] itemQualityZero  = Arrays.stream(items)
            .map( s -> new Item(s.name, s.sellIn, 0))
            .toArray( Item[]::new);
        GildedRose app = new GildedRose(itemQualityZero);

        //when
        app.updateQuality();

        //then
        Arrays.stream(app.items).forEach(item ->  assertTrue(item.quality >= 0));
    }

    @Test
    void agedBrieQualityIncreases() {
        //given
        Item[] agedBre = new Item[] {new Item("Aged Brie", 2, 0)};
        GildedRose app = new GildedRose(agedBre);

        //when
        app.updateQuality();

        //then
        assertEquals(1, app.items[0].quality);
    }

    @Test
    void qualityOfItemIsLessThanFifty() {
        //given
        Item[] itemsExceptSulfura  = Arrays.stream(items)
            .filter(item -> !item.name.equals("Sulfuras, Hand of Ragnaros"))
            .map( s -> new Item(s.name, s.sellIn, 50))
            .toArray( Item[]::new);
        GildedRose app = new GildedRose(itemsExceptSulfura);

        //when
        app.updateQuality();

        //then
        Arrays.stream(app.items).forEach(item ->  assertTrue(item.quality <= 50));
    }

    @Test
    void qualityOfSulfuraNeverDecreases() {
        //given
        Item[] itemSulfura  = Arrays.stream(items)
            .filter(item -> item.name.equals("Sulfuras, Hand of Ragnaros"))
            .map( s -> new Item(s.name, s.sellIn, 80))
            .toArray( Item[]::new);
        GildedRose app = new GildedRose(itemSulfura);

        //when
        app.updateQuality();

        //then
        Arrays.stream(app.items).forEach(item ->  assertTrue(item.quality == 80));
    }


    @Test
    void qualityOfBackStagePassesIncreasesDependingOnDaysLeft() {
        //given
        Item[] itemSulfura = new Item[]{
            new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 10, 30),
            new Item("Backstage passes to a TAFKAL80ETC concert", 7, 30),
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 40),
            new Item("Backstage passes to a TAFKAL80ETC concert", 0, 40)};

        GildedRose app = new GildedRose(itemSulfura);

        //when
        app.updateQuality();

        //then
        assertEquals(21, app.items[0].quality);
        assertEquals(32, app.items[1].quality);
        assertEquals(32, app.items[2].quality);
        assertEquals(43, app.items[3].quality);
        assertEquals(0, app.items[4].quality);

    }

    @Test
    void conjureDegradeTwiceQualityIncreases() {
        //given
        Item[] conjured = new Item[] {new Item("Conjured Mana Cake", 2, 6)};
        GildedRose app = new GildedRose(conjured);

        //when
        app.updateQuality();

        //then
        assertEquals(4, app.items[0].quality);
    }


}
