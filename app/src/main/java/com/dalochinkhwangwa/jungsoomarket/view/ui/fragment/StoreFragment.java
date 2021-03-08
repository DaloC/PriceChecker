package com.dalochinkhwangwa.jungsoomarket.view.ui.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dalochinkhwangwa.jungsoomarket.R;
import com.dalochinkhwangwa.jungsoomarket.model.data.JungsooItem;
import com.dalochinkhwangwa.jungsoomarket.view.adapter.JungsooItemAdapter;
import com.dalochinkhwangwa.jungsoomarket.viewmodel.JungsooViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

public class StoreFragment extends Fragment implements JungsooItemAdapter.StoreToCartDelegate {

    private RecyclerView storeItemsRecyclerView;
    private JungsooViewModel jungsooViewModel;
    private JungsooItemAdapter jungsooItemAdapter = new JungsooItemAdapter(this, new ArrayList<>());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.store_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        storeItemsRecyclerView = view.findViewById(R.id.store_items_recyclerview);
        storeItemsRecyclerView.setAdapter(jungsooItemAdapter);

        jungsooViewModel = new ViewModelProvider(requireActivity()).get(JungsooViewModel.class);
        jungsooViewModel.storeData.observe(getViewLifecycleOwner(), this::updateRecyclerView);

    }

    private void updateRecyclerView(List<JungsooItem> items) {
        jungsooItemAdapter.updateStoreItems(items);
    }

    @Override
    public void addItemToCart(JungsooItem jungsooItem) {
        jungsooViewModel.addItemToCart(jungsooItem);

        Snackbar sBar = Snackbar.make(this.getView(), getString(R.string.item_added_text, jungsooItem.getName()), Snackbar.LENGTH_SHORT);
        View view = sBar.getView();
        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        sBar.show();
    }
}
