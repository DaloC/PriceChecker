package com.dalochinkhwangwa.jungsoomarket.view.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dalochinkhwangwa.jungsoomarket.R;
import com.dalochinkhwangwa.jungsoomarket.model.data.CartItem;
import com.dalochinkhwangwa.jungsoomarket.model.data.JungsooItem;
import com.dalochinkhwangwa.jungsoomarket.view.adapter.CartItemAdapter;
import com.dalochinkhwangwa.jungsoomarket.viewmodel.JungsooViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

public class CartFragment extends Fragment implements CartItemAdapter.CartDelegate {

    private JungsooViewModel jungsooViewModel;
    private RecyclerView cartRecyclerView;
    private CartItemAdapter cartItemAdapter = new CartItemAdapter(this, new ArrayList<>());
    private TextView totalCostTextView;
    private TextView emptyTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cart_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        jungsooViewModel = new ViewModelProvider(requireActivity()).get(JungsooViewModel.class);

        cartRecyclerView = view.findViewById(R.id.cart_recyclerview);
        cartRecyclerView.setAdapter(cartItemAdapter);

        emptyTextView = view.findViewById(R.id.cart_empty_textview);
        totalCostTextView = view.findViewById(R.id.cart_total_texview);
        totalCostTextView.setText(getString(R.string.total_cost_text, 0.0));

        jungsooViewModel.cartItemsData.observe(getViewLifecycleOwner(), this::updateCartRecyclerView);
        jungsooViewModel.cartTotalData.observe(getViewLifecycleOwner(), this::updateCartTotal);
    }

    private void updateCartTotal(Double totalPrice) {
        totalCostTextView.setText(getString(R.string.total_cost_text, totalPrice));
    }

    private void updateCartRecyclerView(List<CartItem> cartItems) {
        emptyTextView.setVisibility(View.GONE);
        cartItemAdapter.updateCartItems(cartItems);
    }

    @Override
    public void addItemToCart(JungsooItem jungsooItem) {
        jungsooViewModel.addItemToCart(jungsooItem);
    }

    @Override
    public void removeItemFromCart(JungsooItem jungsooItem) {
        jungsooViewModel.removeItemFromCart(jungsooItem);
    }
}
