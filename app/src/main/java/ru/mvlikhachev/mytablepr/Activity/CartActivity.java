package ru.mvlikhachev.mytablepr.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.mvlikhachev.mytablepr.Adapter.CartListAdapter;
import ru.mvlikhachev.mytablepr.Domain.RestoranDomain;
import ru.mvlikhachev.mytablepr.Interface.ChangeNumberItemsListener;
import ru.mvlikhachev.mytablepr.Helper.ManagementCart;
import ru.mvlikhachev.mytablepr.R;

public class CartActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;
    private TextView totalFeeTxt,taxTxt,deliveryTxt ;
    public TextView totalTxt;
    private CartListAdapter cartListAdapter;
    private double tax;
    private ScrollView scrollView;
    private ConstraintLayout orderbtn,profileIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        managementCart= new ManagementCart(this);

        initView();
        initList();
        bottomNavigation();


    }

    protected void bottomNavigation(){
    }
    private void initSwipeToDelete() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(cartListAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewList);
    }
    protected void initList() {
//        profileIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, PICK_IMAGE_REQUEST);
//            }
//        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewList.setLayoutManager( linearLayoutManager);
        adapter= new CartListAdapter((ArrayList<RestoranDomain>) managementCart.getListCart(), this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                calculateCard();
            }
        });
        recyclerViewList.setAdapter(adapter);
        if (managementCart.getListCart().isEmpty()){

            scrollView.setVisibility(View.GONE);

        }
        else {

            scrollView.setVisibility(View.VISIBLE);
        }

        cartListAdapter = new CartListAdapter((ArrayList<RestoranDomain>) managementCart.getListCart(), this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                calculateCard();
            }
        });
        recyclerViewList.setAdapter(cartListAdapter);
        initSwipeToDelete();

    }

    protected void calculateCard() {
    }

    protected void initView() {
        profileIcon = findViewById(R.id.profile_icon);
        recyclerViewList = findViewById(R.id.view);
        scrollView = findViewById(R.id.scrollView);
    }
}