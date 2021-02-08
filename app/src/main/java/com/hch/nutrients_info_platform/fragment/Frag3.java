package com.hch.nutrients_info_platform.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.hch.nutrients_info_platform.activity.DetailActivity;
import com.hch.nutrients_info_platform.R;
import com.hch.nutrients_info_platform.adapter.ViewHolder;
import com.hch.nutrients_info_platform.model.Model;

import java.io.ByteArrayOutputStream;

public class Frag3 extends Fragment // Fragment 클래스를 상속받아야한다
{
    private View view;
    LinearLayoutManager mLayoutManager; //for sorting
    SharedPreferences mSharedPref; //for saving sort settings
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;
    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.frag3,container,false);

        mSharedPref = getActivity().getSharedPreferences("SortSettings", getActivity().MODE_PRIVATE);
        String mSorting = mSharedPref.getString("Sort", "oldest"); //where if no settings is selected newest will be default

        //정렬
        if (mSorting.equals("newest")) {
            mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            //this will load the items from bottom means newest first
            mLayoutManager.setReverseLayout(true);
            mLayoutManager.setStackFromEnd(true);
        } else if (mSorting.equals("oldest")) {
            mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            //this will load the items from bottom means oldest first
            mLayoutManager.setReverseLayout(false);
            mLayoutManager.setStackFromEnd(false);
        }

        //RecyclerView
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        //set layout as LinearLayout
        mRecyclerView.setLayoutManager(mLayoutManager);

        //send Query to FirebaseDatabase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("All");

        searchView = (SearchView) view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Filter as you type
                //firebaseSearch(newText);
                return false;
            }
        });

        return view;
    }

    //검색 데이터
    private void firebaseSearch(String searchText) {

        Query firebaseSearchQuery = mRef.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerAdapter<Model, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Model, ViewHolder>(
                        Model.class,
                        R.layout.row,
                        ViewHolder.class,
                        firebaseSearchQuery
                ) {

                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, Model model, int position) {
                        viewHolder.setDetails(getContext().getApplicationContext(), model.getName(), model.getPrice(), model.getImage(), model.getIngredient(), model.getHow(), model.getPrecautions());
                    }

                    @Override
                    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                        ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);

                        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //Views
                                TextView mProductName = view.findViewById(R.id.product_name);
                                TextView mPrice = view.findViewById(R.id.price);
                                TextView mIngredient = view.findViewById(R.id.ingredient);
                                TextView mHow = view.findViewById(R.id.how);
                                TextView mPrecaution = view.findViewById(R.id.precaution);
                                ImageView mImageView = view.findViewById(R.id.rImageView);
                                //get data from views
                                String mNames = mProductName.getText().toString();
                                String mPrices = mPrice.getText().toString();
                                String mIngredients = mIngredient.getText().toString();
                                String mHows = mHow.getText().toString();
                                String mPrecautions = mPrecaution.getText().toString();
                                Drawable mDrawable = mImageView.getDrawable();
                                Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();

                                //pass this data to new activity
                                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                byte[] bytes = stream.toByteArray();
                                intent.putExtra("image", bytes); //put bitmap image as array of bytes
                                intent.putExtra("name", mNames); // put title
                                intent.putExtra("price", mPrices); //put description
                                intent.putExtra("ingredient", mIngredients); // put title
                                intent.putExtra("how", mHows); // put title
                                intent.putExtra("precautions", mPrecautions); // put title
                                startActivity(intent); //start activity


                            }

                            @Override
                            public void onItemLongClick(View view, int position) {
                                //TODO do your own implementaion on long item click
                            }
                        });

                        return viewHolder;
                    }

                };
        //set adapter to recyclerview
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    //시작 시 recycleview 데이터 로드
    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Model, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Model, ViewHolder>(
                        Model.class,
                        R.layout.row,
                        ViewHolder.class,
                        mRef
                ) {

                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, Model model, int position) {
                        viewHolder.setDetails(getContext().getApplicationContext(), model.getName(), model.getPrice(), model.getImage(), model.getIngredient(), model.getHow(), model.getPrecautions());
                    }

                    @Override
                    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                        ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);

                        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //Views
                                TextView mProductName = view.findViewById(R.id.product_name);
                                TextView mPrice = view.findViewById(R.id.price);
                                TextView mIngredient = view.findViewById(R.id.ingredient);
                                TextView mHow = view.findViewById(R.id.how);
                                TextView mPrecaution = view.findViewById(R.id.precaution);
                                ImageView mImageView = view.findViewById(R.id.rImageView);
                                //get data from views
                                String mNames = mProductName.getText().toString();
                                String mPrices = mPrice.getText().toString();
                                String mIngredients = mIngredient.getText().toString();
                                String mHows = mHow.getText().toString();
                                String mPrecautions = mPrecaution.getText().toString();
                                Drawable mDrawable = mImageView.getDrawable();
                                Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();

                                //pass this data to new activity
                                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                byte[] bytes = stream.toByteArray();
                                intent.putExtra("image", bytes); //put bitmap image as array of bytes
                                intent.putExtra("name", mNames); // put title
                                intent.putExtra("price", mPrices); //put description
                                intent.putExtra("ingredient", mIngredients); // put title
                                intent.putExtra("how", mHows); // put title
                                intent.putExtra("precautions", mPrecautions); // put title
                                startActivity(intent); //start activity


                            }

                            @Override
                            public void onItemLongClick(View view, int position) {
                                //TODO do your own implementaion on long item click
                            }
                        });

                        return viewHolder;
                    }

                };
        //set adapter to recyclerview
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}