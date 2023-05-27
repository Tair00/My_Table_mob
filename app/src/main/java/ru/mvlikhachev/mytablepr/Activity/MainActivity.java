package ru.mvlikhachev.mytablepr.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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
import ru.mvlikhachev.mytablepr.Interface.RetrofitInterface;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewCategory();
        recyclerViewPopular();
        bottomNavigation();

//        orderlist1.clear();
        fullOrderlist.clear();
        setProductRecycler(orderlist1);

        fetchRestaurantsFromServer();
    }

    private void setProductRecycler(ArrayList<RestoranDomain> restorans) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        productRecycler = findViewById(R.id.restoranRecycler);
        productRecycler.setLayoutManager(layoutManager);
        priceAdapter = new RestoranAdapter(this);
        productRecycler.setAdapter(priceAdapter);
        productRecycler.smoothScrollToPosition(100000);
        productRecycler.setHasFixedSize(true);
        priceAdapter.updateProducts(restorans);
    }

    // Остальной код остается без изменений


    private void bottomNavigation(){
        LinearLayout profileBtn = findViewById(R.id.profileBtn);
        LinearLayout homeBtn=findViewById(R.id.homeBtn);
        LinearLayout cartBtn =findViewById(R.id.cartBtn);
        LinearLayout setting =findViewById(R.id.setting);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MainActivity.class));
            }
        });
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,CartActivity.class));
            }
        });
    }

    private void recyclerViewPopular() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewPopularList = findViewById(R.id.view2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);
        orderlist.clear();
        orderlist.add(new IventDomain("Акция, английский полдник","ivent","Закажи столик в ресторане ....\n" + "и получи чашку чая в подарок "));
        orderlist.add(new IventDomain("Акция, английский полдник","ivent","Закажи столик в ресторане ....\n" + "и получи чашку чая в подарок "));
        adapter2 = new IventAdapter(orderlist);
        recyclerViewPopularList.setAdapter(adapter2);
    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCategoryList=findViewById(R.id.view1);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);
        categoryList.clear();
        categoryList.add(new CategoryDomain("Китайская кухня", "cat_1",1));
        categoryList.add(new CategoryDomain("Итальянская кухня", "cat_2",2));
        categoryList.add(new CategoryDomain("Стейк хаус", "cat_3",3));
        categoryList.add(new CategoryDomain("Суши", "cat_4",4));
        adapter =new CategoryAdapter(categoryList);
        recyclerViewCategoryList.setAdapter(adapter);
    }

    public static void showOrderByCategory(int category){
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://losermaru.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<RestoranDomain>> call = apiService.getRestaurants();
        call.enqueue(new Callback<List<RestoranDomain>>() {
            @Override
            public void onResponse(Call<List<RestoranDomain>> call, Response<List<RestoranDomain>> response) {
                if (response.isSuccessful()) {
                    List<RestoranDomain> restaurants = response.body();
                    if (restaurants != null) {
                        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        orderlist1.clear();
                        orderlist1.addAll(restaurants);
                        setProductRecycler(orderlist1);
                        priceAdapter.notifyDataSetChanged();
                    }
                } else {
                    System.out.println("---------------------------------------------------------------------------");
                    // Обработка ошибки
                }
            }

            @Override
            public void onFailure(Call<List<RestoranDomain>> call, Throwable t) {
                // Обработка ошибки
            }
        });
    }

}