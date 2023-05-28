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
import ru.mvlikhachev.mytablepr.Adapter.ImgurApiClient;
import ru.mvlikhachev.mytablepr.Adapter.ImgurResponse;
import ru.mvlikhachev.mytablepr.Domain.RestoranDomain;
import ru.mvlikhachev.mytablepr.Helper.ManagementCart;
import ru.mvlikhachev.mytablepr.Interface.CartListener;
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

    // Создание экземпляра ManagementCart с текущей активностью в качестве Context и CartListener


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        // Инициализация объектов пользовательского интерфейса
        initView();

        // Получение данных из переданного объекта
        getBundle();
        CartActivity cartActivity = new CartActivity();

        // Создание экземпляра ManagementCart с текущей активностью в качестве Context и CartActivity в качестве CartActivity
        managementCart = ManagementCart.getInstance(this, cartActivity);
        // Настройка обработчиков событий для кнопок
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

    private void setupButtonListeners() {
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
                intent1.putExtra("feeTxt", title); // Передача значения feeTxt в BookingActivity2
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
        // Обработка обновления корзины
        // В данном примере просто выводим уведомление Toast
        Toast.makeText(this, "Корзина была обновлена", Toast.LENGTH_SHORT).show();
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
