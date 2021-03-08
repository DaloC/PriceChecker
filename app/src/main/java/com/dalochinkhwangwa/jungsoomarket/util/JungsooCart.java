package com.dalochinkhwangwa.jungsoomarket.util;

import com.dalochinkhwangwa.jungsoomarket.model.data.CartItem;
import com.dalochinkhwangwa.jungsoomarket.model.data.JungsooItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.subjects.PublishSubject;

import static com.dalochinkhwangwa.jungsoomarket.util.Logger.logError;

public class JungsooCart {

    public PublishSubject<List<CartItem>> cartItems = PublishSubject.create();
    public PublishSubject<Double> cartTotal = PublishSubject.create();

    private Map<JungsooItem, Integer> cartObject = new LinkedHashMap<>();

    public void addToCart(JungsooItem item){
        int count = 1;
        if(cartObject.containsKey(item))
            count = cartObject.get(item) + 1;
        cartObject.put(item, count);

        cartTotal.onNext(totalPrice());
        cartItems.onNext(getCartItems());
    }

    public void removeFromCart(JungsooItem item){
        if(cartObject.get(item) == 1)
            cartObject.remove(item);
        else{
            int count = cartObject.get(item);
            count -= 1;
            cartObject.put(item, count);
        }

        cartTotal.onNext(totalPrice());
        cartItems.onNext(getCartItems());
    }

    private List<CartItem> getCartItems() {
        List<CartItem> items = new ArrayList<>();
        for(Map.Entry<JungsooItem, Integer> entry: cartObject.entrySet())
            items.add(new CartItem(entry.getKey(), entry.getValue()));
        return items;
    }

    public double totalPrice(){
        double total = 0.0;
        for(Map.Entry<JungsooItem, Integer> entry: cartObject.entrySet())
            total += parsePrice(entry.getKey().getPrice()) * entry.getValue();
        return total;
    }
    public static double parsePrice(String price){
        try {
            return Double.parseDouble(price.substring(1));
        } catch (NumberFormatException e){
            logError(e.getLocalizedMessage());
            return 0;
        }
    }
}
