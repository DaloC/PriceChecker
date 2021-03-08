package com.dalochinkhwangwa.jungsoomarket.viewmodel;

import android.app.Application;

import com.dalochinkhwangwa.jungsoomarket.model.data.CartItem;
import com.dalochinkhwangwa.jungsoomarket.model.data.JungsooItem;
import com.dalochinkhwangwa.jungsoomarket.model.network.JungsooServerMock;
import com.dalochinkhwangwa.jungsoomarket.util.DataState;
import com.dalochinkhwangwa.jungsoomarket.util.JungsooCart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.dalochinkhwangwa.jungsoomarket.util.DataState.COMPLETED;
import static com.dalochinkhwangwa.jungsoomarket.util.DataState.EMPTY;
import static com.dalochinkhwangwa.jungsoomarket.util.DataState.ERROR;
import static com.dalochinkhwangwa.jungsoomarket.util.DataState.LOADING;
import static com.dalochinkhwangwa.jungsoomarket.util.Logger.logError;

public class JungsooViewModel extends AndroidViewModel {

    public MutableLiveData<List<JungsooItem>> storeData = new MutableLiveData<>();
    public MutableLiveData<DataState> dataState = new MutableLiveData<>();
    public MutableLiveData<String> insertedData = new MutableLiveData<>();

    public MutableLiveData<List<CartItem>> cartItemsData = new MutableLiveData<>();
    public MutableLiveData<Double> cartTotalData = new MutableLiveData<>();

    private Map<String, JungsooItem> scanMap = new HashMap<>();

    private final JungsooCart userCart = new JungsooCart();

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public JungsooViewModel(@NonNull Application application) {
        super(application);
        dataState.setValue(LOADING);

        compositeDisposable.addAll(
                JungsooServerMock.getStoreItems(application.getApplicationContext())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(itemList -> {

                    if(itemList.isEmpty())
                        dataState.setValue(EMPTY);
                    else {
                        dataState.setValue(COMPLETED);
                        storeData.setValue(itemList);
                        addToScanMap(itemList);
                    }

                }, throwable -> {
                    dataState.setValue(ERROR);
                    logError(throwable.getLocalizedMessage());
                }),

                userCart.cartItems.observeOn(
                        AndroidSchedulers.mainThread()
                ).subscribeOn(Schedulers.io())
                .subscribe(cartItems -> {
                    cartItemsData.postValue(cartItems);
                }, throwable -> {
                    logError(throwable.getLocalizedMessage());
                }),

                userCart.cartTotal
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(totalPrice -> {
                    cartTotalData.postValue(totalPrice);
                }, throwable -> {
                    logError(throwable.getLocalizedMessage());
                })
        );
    }

    private void addToScanMap(List<JungsooItem> itemList) {
        itemList.forEach(jungsooItem -> {
            scanMap.put(jungsooItem.getId(), jungsooItem);
        });
    }

    public void addItemToCart(JungsooItem item){
        userCart.addToCart(item);
    }

    public void removeItemFromCart(JungsooItem item){
        userCart.removeFromCart(item);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    public void scannedItem(String itemId) {
        addItemToCart(scanMap.get(itemId));
        insertedData.postValue(scanMap.get(itemId).getName());
    }
}
