package ru.mvlikhachev.mytablepr.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mvlikhachev.mytablepr.Adapter.ImgurApiClient;
import ru.mvlikhachev.mytablepr.Adapter.ImgurResponse;
import ru.mvlikhachev.mytablepr.Domain.RestaurantResponse;
import ru.mvlikhachev.mytablepr.Domain.RestoranDomain;
import ru.mvlikhachev.mytablepr.Helper.ManagementCart;
import ru.mvlikhachev.mytablepr.Interface.CartListener;
import ru.mvlikhachev.mytablepr.Interface.RestaurantAPI;
import ru.mvlikhachev.mytablepr.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ShowDetailActivity extends AppCompatActivity implements CartListener {
    private TextView addToCartBtn;
    private TextView titleTxt, feeTxt, description, starTxt, tableTxt;
    private ImageView heart, restoranPic;
    private RestoranDomain object;
    private TextView numberOrderTxt;
    private ImageView plusBtn, minusBtn;
    private ManagementCart managementCart;
    private int numberOrder = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        initView();
        getBundle();
        CartActivity cartActivity = new CartActivity();
        managementCart = ManagementCart.getInstance(this, cartActivity);
        setupButtonListeners();
    }

    private void getBundle() {
        object = (RestoranDomain) getIntent().getSerializableExtra("object");
        if (object != null) {
            titleTxt.setText(object.getName());
            feeTxt.setText(String.valueOf(object.getPrice()));
            description.setText(object.getDescription());
            starTxt.setText(String.valueOf(object.getStar()));

            Picasso.get().load(object.getPicture()).into(restoranPic);
        } else {
            // Обработка ошибки: объект RestoranDomain не передан
        }
    }

    private void fetchRestaurantData(String restaurantId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://losermaru.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RestaurantAPI restaurantAPI = retrofit.create(RestaurantAPI.class);
        Call<RestaurantResponse> call = restaurantAPI.getRestaurantById(restaurantId);

        call.enqueue(new Callback<RestaurantResponse>() {
            @Override
            public void onResponse(Call<RestaurantResponse> call, Response<RestaurantResponse> response) {
                if (response.isSuccessful()) {
                    RestaurantResponse restaurantResponse = response.body();
                    if (restaurantResponse != null) {
                        // Получите данные о ресторане из restaurantResponse и отобразите их на экране или выполните нужные действия
                        Toast.makeText(ShowDetailActivity.this, "Вы успешно забронировали столик", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Обработка ошибки при получении данных
                    Toast.makeText(ShowDetailActivity.this, "Ошибка при получении данных", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RestaurantResponse> call, Throwable t) {
                // Обработка ошибки при выполнении запроса
                Toast.makeText(ShowDetailActivity.this, "Ошибка: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupButtonListeners() {
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = feeTxt.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("title", title);
                editor.apply();
                String email = getIntent().getStringExtra("email");

                // Передаем email в BookingActivity2

                Integer restaurantId = getIntent().getIntExtra("restorantId",0);

                Intent intent1 = new Intent(ShowDetailActivity.this, BookingActivity2.class);
                intent1.putExtra("email", email);
                intent1.putExtra("restorantId", restaurantId);
                System.out.println("restaurantId +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+restaurantId);
                intent1.putExtra("feeTxt", title);
                intent1.putExtra("restoranPic", object.getPicture()); // Pass the restoranPic value
                startActivity(intent1);
            }
        });

        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementCart.addItem(object);
            }
        });

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOrder++;
                updateOrderQuantity();
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOrder > 1) {
                    numberOrder--;
                    updateOrderQuantity();
                }
            }
        });
    }

    private void updateOrderQuantity() {
        numberOrderTxt.setText(String.valueOf(numberOrder));
        feeTxt.setText(String.valueOf(numberOrder * object.getPrice()));
    }

    @Override
    public void onCartUpdated() {
        Toast.makeText(this, "Добавлено в избранное", Toast.LENGTH_SHORT).show();
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