package ru.mvlikhachev.mytablepr.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    String serverUrl = "https://losermaru.pythonanywhere.com/table";
    static ArrayList<TableDomain> tableList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking2);
        showTimePickerDialog();
        setTableRecycler(tableList);
        showDatePickerDialog();

        // Выполнение GET-запроса к серверу
        executeGetRequest();
    }

    private void setTableRecycler(ArrayList<TableDomain> table) {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        tableRecycler = findViewById(R.id.table_recycler);
        tableRecycler.setLayoutManager(layoutManager);
        tableAdapter = new TableAdapter(this, table);
        tableRecycler.setAdapter(tableAdapter);
        tableRecycler.smoothScrollToPosition(0);
        tableRecycler.setHasFixedSize(true);
    }

    public void showTimePickerDialog() {
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog() {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void executeGetRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, serverUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Обработка успешного ответа от сервера
                        parseResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Обработка ошибки запроса
                        Toast.makeText(BookingActivity2.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(stringRequest);
    }

    private void parseResponse(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String title = jsonObject.getString("number");
                String seat = jsonObject.getString("seat");
//                String pic = jsonObject.getString("pic");

                tableList.add(new TableDomain(id, title, seat));
            }

            // Уведомление адаптера об изменении данных
            tableAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(BookingActivity2.this, "Error parsing response", Toast.LENGTH_SHORT).show();
        }
    }
}