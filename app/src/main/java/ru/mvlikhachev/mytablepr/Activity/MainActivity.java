package ru.mvlikhachev.mytablepr.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mvlikhachev.mytablepr.Adapter.CategoryAdapter;
import ru.mvlikhachev.mytablepr.Adapter.IventAdapter;
import ru.mvlikhachev.mytablepr.Adapter.RestoranAdapter;
import ru.mvlikhachev.mytablepr.Domain.CategoryDomain;
import ru.mvlikhachev.mytablepr.Domain.IventDomain;
import ru.mvlikhachev.mytablepr.Domain.RestoranDomain;
import ru.mvlikhachev.mytablepr.Interface.ApiService;
import ru.mvlikhachev.mytablepr.R;
public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList, productRecycler;
    static ArrayList<IventDomain> orderlist = new ArrayList<>();
    static ArrayList<RestoranDomain> orderlist1 = new ArrayList<>();
    static ArrayList<CategoryDomain> categoryList = new ArrayList<>();
    static ArrayList<RestoranDomain> fullOrderlist = new ArrayList<>();
    public static IventAdapter iventAdapter;
    static RestoranAdapter priceAdapter;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewCategory();
        recyclerViewPopular();
        bottomNavigation();
        fullOrderlist.clear();
        setProductRecycler(orderlist1);

        // Получение токена из предыдущего экрана (например, LoginActivity)
        token = getIntent().getStringExtra("access_token");

        fetchRestaurantsFromServer();
    }

    private void setProductRecycler(ArrayList<RestoranDomain> restorans) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        String email = getIntent().getStringExtra("email");
        System.out.println("====================================" + token);
        productRecycler = findViewById(R.id.restoranRecycler);
        productRecycler.setLayoutManager(layoutManager);
        priceAdapter = new RestoranAdapter(this, email,token);
        productRecycler.setAdapter(priceAdapter);
        productRecycler.smoothScrollToPosition(100000);
        productRecycler.setHasFixedSize(true);
        priceAdapter.updateProducts(restorans);
    }

    private void bottomNavigation() {
        LinearLayout profileBtn = findViewById(R.id.profileBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout cartBtn = findViewById(R.id.cartBtn);
        LinearLayout setting = findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, BookingListActivity.class);
                String  token = getIntent().getStringExtra("access_token");
                String email = getIntent().getStringExtra("email");
                intent1.putExtra("email", email);
                intent1.putExtra("access_token", token);
                startActivity(intent1);
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });
    }

    private void recyclerViewPopular() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList = findViewById(R.id.view2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);
        orderlist.clear();
        orderlist.add(new IventDomain("Акция, английский полдник", "ivent", "Закажи столик в ресторане ....\n" + "и получи чашку чая в подарок "));
        orderlist.add(new IventDomain("Акция, английский полдник", "ivent", "Закажи столик в ресторане ....\n" + "и получи чашку чая в подарок "));
        adapter2 = new IventAdapter(orderlist);
        recyclerViewPopularList.setAdapter(adapter2);
    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = findViewById(R.id.view1);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);
        categoryList.clear();
        categoryList.add(new CategoryDomain("Китайская кухня", "cat_1", 1));
        categoryList.add(new CategoryDomain("Итальянская кухня", "cat_2", 2));
        categoryList.add(new CategoryDomain("Стейк хаус", "cat_3", 3));
        categoryList.add(new CategoryDomain("Суши", "cat_4", 4));
        adapter = new CategoryAdapter(categoryList);
        recyclerViewCategoryList.setAdapter(adapter);
    }

    public static void showOrderByCategory(int category) {
        orderlist1.clear();
        orderlist1.addAll(fullOrderlist);
        List<RestoranDomain> filterOrder = new ArrayList<>();
        for (RestoranDomain c : fullOrderlist) {
            if (c.getCat_id() == category)
                filterOrder.add(c);
        }
        orderlist1.clear();
        orderlist1.addAll(filterOrder);
        priceAdapter.notifyDataSetChanged();
    }

    private void fetchRestaurantsFromServer() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                // Создание нового запроса с добавленным заголовком авторизации
                Request newRequest = originalRequest.newBuilder()
                        .header("Authorization", "Bearer " + token)
                        .build();

                return chain.proceed(newRequest);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://losermaru.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<RestoranDomain>> call = apiService.getRestaurants();
        call.enqueue(new Callback<List<RestoranDomain>>() {
            @Override
            public void onResponse(Call<List<RestoranDomain>> call, Response<List<RestoranDomain>> response) {
                if (response.isSuccessful()) {
                    List<RestoranDomain> restaurants = response.body();
                    if (restaurants != null) {
                        orderlist1.clear();
                        orderlist1.addAll(restaurants);
                        setProductRecycler(orderlist1);
                        priceAdapter.notifyDataSetChanged();
                    }
                } else {
                    // Обработка ошибки
                }
            }

            @Override
            public void onFailure(Call<List<RestoranDomain>> call, Throwable t) {

            }
        });
    }
}
