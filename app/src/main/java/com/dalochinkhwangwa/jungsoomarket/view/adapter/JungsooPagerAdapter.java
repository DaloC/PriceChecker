package com.dalochinkhwangwa.jungsoomarket.view.adapter;

import com.dalochinkhwangwa.jungsoomarket.view.ui.fragment.CartFragment;
import com.dalochinkhwangwa.jungsoomarket.view.ui.fragment.StoreFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import static com.dalochinkhwangwa.jungsoomarket.util.Constants.*;

public class JungsooPagerAdapter extends FragmentPagerAdapter {

    public static final int FRAGMENT_COUNT = 2;
    public JungsooPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment displayFragment = null;
        switch (position){
            case STORE_FRAGMENT:
                displayFragment = new StoreFragment();
                break;
            case CART_FRAGMENT:
                displayFragment = new CartFragment();
                break;
        }
        return displayFragment;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }
}
