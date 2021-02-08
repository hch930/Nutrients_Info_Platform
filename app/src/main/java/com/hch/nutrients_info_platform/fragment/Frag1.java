package com.hch.nutrients_info_platform.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.hch.nutrients_info_platform.activity.DetailActivity;
import com.hch.nutrients_info_platform.R;
import com.hch.nutrients_info_platform.adapter.ViewHolder;
import com.hch.nutrients_info_platform.model.Model;

import java.io.ByteArrayOutputStream;

public class Frag1 extends Fragment // Fragment 클래스를 상속받아야한다
{
    private View view;
    private LinearLayout linLayout;
    private TextView taurine, arginine, booster, loseWeight, bcaa, vitaminc, vitaminb, protein;
    private LinearLayoutManager mLayoutManager; //for sorting
    private SharedPreferences mSharedPref; //for saving sort settings
    private RecyclerView mRecyclerView1;
    private DatabaseReference mRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.frag1,container,false);
        linLayout = (LinearLayout)view.findViewById(R.id.linLayout);
        taurine = (TextView) view.findViewById(R.id.taurine);
        arginine = (TextView) view.findViewById(R.id.arginine);
        booster = (TextView) view.findViewById(R.id.booster);
        loseWeight = (TextView) view.findViewById(R.id.loseWeight);
        protein = (TextView) view.findViewById(R.id.protein);
        bcaa = (TextView) view.findViewById(R.id.bcaa);
        vitaminc = (TextView) view.findViewById(R.id.vitaminc);
        vitaminb = (TextView) view.findViewById(R.id.vitaminb);

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
        mRecyclerView1 = view.findViewById(R.id.recyclerView1);
        mRecyclerView1.setHasFixedSize(true);

        //set layout as LinearLayout
        mRecyclerView1.setLayoutManager(mLayoutManager);

        //Textview 클릭 시 해당 카테고리 제품 표시
        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    //타우린
                    case R.id.taurine:
                        linLayout.setVisibility(view.GONE);
                        mRef = FirebaseDatabase.getInstance().getReference("Taurines");
                        mRecyclerView1.setVisibility(view.VISIBLE);
                        data();
                        break;
                    //아르기닌
                    case R.id.arginine:
                        linLayout.setVisibility(view.GONE);
                        mRef = FirebaseDatabase.getInstance().getReference("Arginines");
                        mRecyclerView1.setVisibility(view.VISIBLE);
                        data();
                        break;
                    //부스터
                    case R.id.booster:
                        linLayout.setVisibility(view.GONE);
                        mRef = FirebaseDatabase.getInstance().getReference("Boosters");
                        mRecyclerView1.setVisibility(view.VISIBLE);
                        data();
                        break;
                    //체중감량
                    case R.id.loseWeight:
                        linLayout.setVisibility(view.GONE);
                        mRef = FirebaseDatabase.getInstance().getReference("LoseWeight");
                        mRecyclerView1.setVisibility(view.VISIBLE);
                        data();
                        break;
                    //프로틴
                    case R.id.protein:
                        linLayout.setVisibility(view.GONE);
                        mRef = FirebaseDatabase.getInstance().getReference("Protein");
                        mRecyclerView1.setVisibility(view.VISIBLE);
                        data();
                        break;
                    //bcaa
                    case R.id.bcaa:
                        linLayout.setVisibility(view.GONE);
                        mRef = FirebaseDatabase.getInstance().getReference("BCAAs");
                        mRecyclerView1.setVisibility(view.VISIBLE);
                        data();
                        break;
                    //비타민c
                    case R.id.vitaminc:
                        linLayout.setVisibility(view.GONE);
                        mRef = FirebaseDatabase.getInstance().getReference("Vitamin_C");
                        mRecyclerView1.setVisibility(view.VISIBLE);
                        data();
                        break;
                    //비타민b
                    case R.id.vitaminb:
                        linLayout.setVisibility(view.GONE);
                        mRef = FirebaseDatabase.getInstance().getReference("Vitamin_B");
                        mRecyclerView1.setVisibility(view.VISIBLE);
                        data();
                        break;
                }
            }
        };
        taurine.setOnClickListener(onClickListener);
        arginine.setOnClickListener(onClickListener);
        booster.setOnClickListener(onClickListener);
        loseWeight.setOnClickListener(onClickListener);
        protein.setOnClickListener(onClickListener);
        bcaa.setOnClickListener(onClickListener);
        vitaminc.setOnClickListener(onClickListener);
        vitaminb.setOnClickListener(onClickListener);
        return view;

    }

    //load data into recycler view onStart
    public void data() {
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
        mRecyclerView1.setAdapter(firebaseRecyclerAdapter);
    }
}