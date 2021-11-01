package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            QualityCalculatorInterface calculator = getCalculator(items[i]);
            if (calculator != null) {
                items[i].quality = QualityCalculatorInterface
                    .calculate(items[i].quality , calculator.changeQuality(items[i]));
                items[i].sellIn = items[i].sellIn - 1;
            }
        }
    }


    private QualityCalculatorInterface getCalculator(Item item) {

        QualityCalculatorInterface calculator = (Item it) -> it.sellIn > 0? 1: 2;

        if (item.name.equals("Aged Brie"))  {
            calculator = (Item it) -> it.sellIn > 0? -1: -2;

        }
        if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
            calculator = (Item it) -> {
                if(it.sellIn <= 0) return it.quality;
                if(it.sellIn <= 5) return -3;
                if(it.sellIn <=10) return - 2;
                return -1;
            };
        }

        if(item.name.equals("Sulfuras, Hand of Ragnaros")){
            calculator = null;
        }

        if(item.name.equals("Conjured Mana Cake")){
            calculator = (Item it) -> it.sellIn > 0? 2: 4;
        }
        return calculator;
    }

}
