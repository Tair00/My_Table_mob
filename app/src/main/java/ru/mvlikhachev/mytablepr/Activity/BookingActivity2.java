package ru.mvlikhachev.mytablepr.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.mvlikhachev.mytablepr.Adapter.RestoranAdapter;
import ru.mvlikhachev.mytablepr.Adapter.TableAdapter;
import ru.mvlikhachev.mytablepr.Domain.RestoranDomain;
import ru.mvlikhachev.mytablepr.Domain.TableDomain;
import ru.mvlikhachev.mytablepr.Fragment.DatePickerFragment;
import ru.mvlikhachev.mytablepr.Fragment.TimePickerFragment;
import ru.mvlikhachev.mytablepr.R;

public class BookingActivity2 extends AppCompatActivity {
    private RecyclerView tableRecycler;
    static TableAdapter tableAdapter;
    String serverUrl = "https://losermaru.pythonanywhere.com/auth";
    static ArrayList<TableDomain> tableList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking2);
        showTimePickerDialog();
        setTableRecycler(tableList);
        showDatePickerDialog();

    }
    private void setTableRecycler(ArrayList<TableDomain> table) {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);

        tableRecycler = findViewById(R.id.table_recycler);

        tableRecycler.setLayoutManager(layoutManager);

        tableAdapter = new TableAdapter(this, table);
        tableRecycler.setAdapter(tableAdapter);
        tableRecycler.smoothScrollToPosition(100000);
        tableRecycler.setHasFixedSize(true);

        tableList.add(new TableDomain(1,"Стол 1","на 2 персоны","table_cart",1800));
        tableList.add(new TableDomain(4,"Стол 2","на 4 персоны","table_cart",1800));
        tableList.add(new TableDomain(2,"Стол 3","на 2 персоны","table_cart",1800));
        tableList.add(new TableDomain(3,"Стол 4","на 4 персоны","table_cart",1800));
        tableList.add(new TableDomain(5,"Стол 5","на 4 персоны","table_cart",1800));
        tableList.add(new TableDomain(6,"Стол 6","на 2 персоны","table_cart",1800));
        tableList.add(new TableDomain(7,"Стол 7","на 4 персоны","table_cart",1800));
        tableList.add(new TableDomain(8,"Стол 8","на 2 персоны","table_cart",1800));

    }

    public void showTimePickerDialog() {
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog() {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

}