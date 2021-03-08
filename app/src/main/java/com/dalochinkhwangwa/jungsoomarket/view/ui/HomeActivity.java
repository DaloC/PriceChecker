package com.dalochinkhwangwa.jungsoomarket.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.dalochinkhwangwa.jungsoomarket.R;
import com.dalochinkhwangwa.jungsoomarket.view.adapter.JungsooPagerAdapter;
import com.dalochinkhwangwa.jungsoomarket.viewmodel.JungsooViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import static com.dalochinkhwangwa.jungsoomarket.util.Constants.CART_FRAGMENT;
import static com.dalochinkhwangwa.jungsoomarket.util.Constants.STORE_FRAGMENT;
import static com.dalochinkhwangwa.jungsoomarket.util.Logger.logDebug;

public class HomeActivity extends AppCompatActivity {

    private JungsooViewModel viewModel;
    private JungsooPagerAdapter jungsooPagerAdapter;
    private ImageView scanImageview;

    private BottomNavigationView bottomNavigationView;
    private ViewPager homeViewPager;

    private ConstraintLayout parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(JungsooViewModel.class);
        bottomNavigationView = findViewById(R.id.home_bottomnavigationview);
        homeViewPager = findViewById(R.id.home_viewpager);

        parentLayout = findViewById(R.id.parent_layout);

        jungsooPagerAdapter = new JungsooPagerAdapter(getSupportFragmentManager());
        scanImageview = findViewById(R.id.scan_imageview);
        scanImageview.setOnClickListener(view -> {
            new IntentIntegrator(this).initiateScan();
        });

        homeViewPager.setAdapter(jungsooPagerAdapter);
        homeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Do Nothing
            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case STORE_FRAGMENT:
                        bottomNavigationView.setSelectedItemId(R.id.shop_item);
                        break;
                    case CART_FRAGMENT:
                        bottomNavigationView.setSelectedItemId(R.id.cart_item);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Do nothing
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.shop_item:
                    homeViewPager.setCurrentItem(STORE_FRAGMENT);
                    break;
                case R.id.cart_item:
                    homeViewPager.setCurrentItem(CART_FRAGMENT);
                    break;
            }
            return true;
        });
        viewModel.insertedData.observe(this, itemName ->{
            parentLayout.setForegroundGravity(Gravity.TOP);
            Snackbar sBar = Snackbar.make(parentLayout, getString(R.string.item_added_text, itemName), Snackbar.LENGTH_SHORT);

            View view = sBar.getView();
            FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
            params.gravity = Gravity.TOP;
            view.setLayoutParams(params);
            sBar.show();

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                logDebug("Scanned: " + result.getContents());
                viewModel.scannedItem(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}