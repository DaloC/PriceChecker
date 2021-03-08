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
import com.dalochinkhwangwa.jungsoomarket.model.data.JungsooItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class JungsooItemAdapter extends RecyclerView.Adapter<JungsooItemAdapter.JungsooItemViewHolder> {

    private StoreToCartDelegate storeToCartDelegate;
    private List<JungsooItem> itemList;
    private Animation scaleAnimation;

    public JungsooItemAdapter(StoreToCartDelegate storeToCartDelegate, List<JungsooItem> itemList) {
        this.storeToCartDelegate = storeToCartDelegate;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public JungsooItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        scaleAnimation = AnimationUtils.loadAnimation(parent.getContext(), R.anim.scale_up);
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.jungsoo_item_layout, parent, false);
        return new JungsooItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JungsooItemViewHolder holder, int position) {
        JungsooItem item = itemList.get(position);
        Glide.with(holder.itemView)
                .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                .load(item.getThumbnail())
                .into(holder.productImageView);
        holder.productNameTextView.setText(item.getName());
        holder.priceTextView.setText(item.getPrice());
        holder.addImageView.setOnClickListener(view ->{
            storeToCartDelegate.addItemToCart(item);
            view.startAnimation(scaleAnimation);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void updateStoreItems(List<JungsooItem> itemList){
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    class JungsooItemViewHolder extends RecyclerView.ViewHolder{

        public ImageView productImageView;
        public TextView productNameTextView;
        public TextView priceTextView;
        private ImageView addImageView;

        public JungsooItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.product_imageview);
            productNameTextView = itemView.findViewById(R.id.product_name_textview);
            priceTextView = itemView.findViewById(R.id.product_price_textview);
            addImageView = itemView.findViewById(R.id.add_imageview);
        }
    }

    public interface StoreToCartDelegate {
        void addItemToCart(JungsooItem jungsooItem);
    }
}
