package com.dalochinkhwangwa.jungsoomarket.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dalochinkhwangwa.jungsoomarket.R;
import com.dalochinkhwangwa.jungsoomarket.model.data.CartItem;
import com.dalochinkhwangwa.jungsoomarket.model.data.JungsooItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.dalochinkhwangwa.jungsoomarket.util.JungsooCart.parsePrice;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder> {

    private CartDelegate cartDelegate;
    private List<CartItem> cartItems;
    private Animation scaleAnimation;

    public CartItemAdapter(CartDelegate cartDelegate, List<CartItem> cartItems) {
        this.cartDelegate = cartDelegate;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        scaleAnimation = AnimationUtils.loadAnimation(parent.getContext(), R.anim.scale_up);
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item_layout, parent, false);
        return new CartItemViewHolder(itemView);
    }

    public void updateCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.productNameTextView.setText(cartItem.getJungsooItem().getName());

        holder.priceTextView.setText(holder.itemView.getContext()
                .getString(
                        R.string.price,
                        (cartItem.getCount() * parsePrice(cartItem.getJungsooItem().getPrice()))));

        holder.removeItemImageView.setOnClickListener(view -> {
            view.startAnimation(scaleAnimation);
            cartDelegate.removeItemFromCart(cartItem.getJungsooItem());
        });

        holder.addItemImageView.setOnClickListener(view -> {
            view.startAnimation(scaleAnimation);
            cartDelegate.addItemToCart(cartItem.getJungsooItem());
        });

        Glide.with(holder.itemView)
                .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                .load(cartItem.getJungsooItem().getThumbnail())
                .into(holder.productImageView);
        holder.itemCountTextView.setText(holder.itemView.getContext().getString(R.string.item_count, cartItem.getCount()));
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    class CartItemViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView;
        ImageView productImageView;
        TextView itemCountTextView;

        ImageView removeItemImageView;
        ImageView addItemImageView;

        TextView priceTextView;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.cart_product_name_textview);
            productImageView = itemView.findViewById(R.id.cart_product_imageview);
            itemCountTextView = itemView.findViewById(R.id.item_count_textview);
            removeItemImageView = itemView.findViewById(R.id.cart_remove_imageview);
            addItemImageView = itemView.findViewById(R.id.cart_add_imageview);
            priceTextView = itemView.findViewById(R.id.cart_product_price_textview);

        }
    }

    public interface CartDelegate {
        void addItemToCart(JungsooItem jungsooItem);

        void removeItemFromCart(JungsooItem jungsooItem);
    }
}
