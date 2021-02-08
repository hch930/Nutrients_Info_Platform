package com.hch.nutrients_info_platform.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.hch.nutrients_info_platform.R;

public class DetailActivity extends AppCompatActivity{
    TextView mProductName, mPrice, mIngredient, mHow, mPrecaution;
    ImageView mImage;

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        //view 초기화
        mProductName = findViewById(R.id.dProduct_name);
        mPrice = findViewById(R.id.dPrice);
        mIngredient = findViewById(R.id.dIngredient);
        mHow = findViewById(R.id.dHow);
        mPrecaution = findViewById(R.id.dPrecaution);
        mImage = findViewById(R.id.dImageView);

        //intent로 데이터 전달
        byte[] bytes = getIntent().getByteArrayExtra("image");
        String title = getIntent().getStringExtra("name");
        String desc = getIntent().getStringExtra("price");
        String desc2 = getIntent().getStringExtra("ingredient");
        String desc3 = getIntent().getStringExtra("how");
        String desc4 = getIntent().getStringExtra("precautions");
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        //data를 view로 설정
        mProductName.setText(title);
        mPrice.setText(desc);
        mImage.setImageBitmap(bmp);
        mIngredient.setText(desc2);
        mHow.setText(desc3);
        mPrecaution.setText(desc4);

        //imageview에서 image를 bitmap으로 가져오기
        bitmap = ((BitmapDrawable) mImage.getDrawable()).getBitmap();
    }
}
