package com.dalochinkhwangwa.jungsoomarket.model.network;

import android.content.Context;

import com.dalochinkhwangwa.jungsoomarket.model.data.JungsooItem;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

import static com.dalochinkhwangwa.jungsoomarket.util.Logger.logDebug;
import static com.dalochinkhwangwa.jungsoomarket.util.Logger.logError;

public class JungsooServerMock {
    private static final String responseFile = "response.json";

    public static Single<List<JungsooItem>> getStoreItems(Context context){
        List<JungsooItem> storeItems = new ArrayList<>();

        try {
            InputStream inputStream = context.getAssets().open(responseFile);

            int length = inputStream.available();
            byte[] buffer = new byte[length];

            inputStream.read(buffer);
            inputStream.close();

            String jsonString = new String(buffer, Charset.defaultCharset());
            Gson gson = new Gson();

            JungsooItem[] items = gson.fromJson(jsonString, JungsooItem[].class);
            storeItems = Arrays.asList(items);
            logDebug(jsonString);

        } catch (IOException e) {
            e.printStackTrace();
            logError(e.getLocalizedMessage());
        }
        return Single.just(storeItems);
    }
}
