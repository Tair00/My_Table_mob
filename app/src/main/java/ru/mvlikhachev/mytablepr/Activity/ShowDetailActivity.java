package ru.mvlikhachev.mytablepr.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.mvlikhachev.mytablepr.Adapter.ImgurApiClient;
import ru.mvlikhachev.mytablepr.Adapter.ImgurResponse;
import ru.mvlikhachev.mytablepr.Domain.RestoranDomain;
import ru.mvlikhachev.mytablepr.Helper.ManagementCart;
import ru.mvlikhachev.mytablepr.R;

public class ShowDetailActivity extends AppCompatActivity {
    private TextView addToCartBtn;
    private TextView titleTxt, feeTxt, description, starTxt, tableTxt;
    private ImageView heart, restoranPic;
    private RestoranDomain object;
    private TextView numberOrderTxt;
    private ImageView plusBtn, minusBtn;

    private int numberOrder = 1;
    private ManagementCart managementCart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        managementCart = new ManagementCart(this);
        initView();
        getBundle();
    }

    private void getBundle() {
        object = (RestoranDomain) getIntent().getSerializableExtra("object");
        if (object != null) {
            titleTxt.setText(object.getTitle());
            feeTxt.setText(String.valueOf(object.getPrice()));
            description.setText(object.getDescription());
            starTxt.setText(String.valueOf(object.getStar()));

            ImgurApiClient.getClient().getImgurImage(object.getPic()).enqueue(new Callback<ImgurResponse>() {
                @Override
                public void onResponse(Call<ImgurResponse> call, Response<ImgurResponse> response) {
                    if (response.isSuccessful()) {
                        ImgurResponse imgurResponse = response.body();
                        if (imgurResponse != null) {
                            String imageUrl = imgurResponse.getData().getLink();
                            Picasso.get().load(imageUrl).into(restoranPic);
                        }
                    } else {
                        // Обработка ошибки загрузки изображения
                    }
                }

                @Override
                public void onFailure(Call<ImgurResponse> call, Throwable t) {
                    // Обработка ошибки загрузки изображения
                }
            });
        } else {
            // Обработка ошибки: объект RestoranDomain не передан
        }

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = feeTxt.getText().toString();

                // Сохранение значения в SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("title", title);
                editor.apply();

                Intent intent1 = new Intent(ShowDetailActivity.this, BookingActivity2.class);
                startActivity(intent1);
            }
        });

        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementCart.addToCart(object);
            }
        });

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOrder = numberOrder + 1;
                numberOrderTxt.setText(String.valueOf(numberOrder));
                feeTxt.setText(String.valueOf(numberOrder * object.getPrice()));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOrder > 1) {
                    numberOrder = numberOrder - 1;
                }
                numberOrderTxt.setText(String.valueOf(numberOrder));
                feeTxt.setText(String.valueOf(numberOrder * object.getPrice()));
            }
        });
    }

    private void initView() {
        tableTxt = findViewById(R.id.tableTxt);
        numberOrderTxt = findViewById(R.id.numberItemTxt);
        heart = findViewById(R.id.heart);
        addToCartBtn = findViewById(R.id.addToCartBtn);
        titleTxt = findViewById(R.id.titleTxt);
        feeTxt = findViewById(R.id.priceTxt);
        description = findViewById(R.id.descriptionTxt);
        restoranPic = findViewById(R.id.restoranPic);
        starTxt = findViewById(R.id.starTxt);
        plusBtn = findViewById(R.id.plusCardBtn);
        minusBtn = findViewById(R.id.minusCardBtn);
    }
}
