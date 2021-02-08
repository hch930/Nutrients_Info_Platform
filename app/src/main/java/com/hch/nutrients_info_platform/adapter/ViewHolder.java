package com.hch.nutrients_info_platform.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hch.nutrients_info_platform.R;
import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {
    View mView;

    public ViewHolder(View itemView) {
        super(itemView);

        mView = itemView;

        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });
        //item long click
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongClick(view, getAdapterPosition());
                return true;
            }
        });

    }

    //set details to recycler view row
    public void setDetails(Context ctx, String name, String price, String image, String ingredient, String how, String precautions){
        //Views
        TextView mProductName = mView.findViewById(R.id.product_name);
        TextView mPrice = mView.findViewById(R.id.price);
        TextView mIngredient = mView.findViewById(R.id.ingredient);
        TextView mHow = mView.findViewById(R.id.how);
        TextView mPrecaution = mView.findViewById(R.id.precaution);
        ImageView mImage = mView.findViewById(R.id.rImageView);
        //set data to views
        mProductName.setText(name);
        mPrice.setText(price);
        mIngredient.setText(ingredient);
        mHow.setText(how);
        mPrecaution.setText(precautions);
        Picasso.get().load(image).into(mImage);
    }

    private ViewHolder.ClickListener mClickListener;

    //interface to send callbacks
    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View  view, int position);
    }

    public void setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }
}
