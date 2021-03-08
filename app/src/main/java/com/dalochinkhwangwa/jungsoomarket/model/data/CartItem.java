package com.dalochinkhwangwa.jungsoomarket.model.data;

public class CartItem {
    private JungsooItem jungsooItem;
    private int count;

    public CartItem(JungsooItem jungsooItem, int count) {
        this.jungsooItem = jungsooItem;
        this.count = count;
    }

    public JungsooItem getJungsooItem() {
        return jungsooItem;
    }

    public void setJungsooItem(JungsooItem jungsooItem) {
        this.jungsooItem = jungsooItem;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
