package ru.mvlikhachev.mytablepr.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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
    private Float price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking2);
        showTimePickerDialog();
        setTableRecycler(tableList);
        showDatePickerDialog();
        String feeTxtValue = getIntent().getStringExtra("feeTxt");
        Float price =Float.parseFloat(feeTxtValue);
        // Выполнение GET-запроса к серверу
        executeGetRequest();
    }

    private void setTableRecycler(ArrayList<TableDomain> table) {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        tableRecycler = findViewById(R.id.table_recycler);
        tableRecycler.setLayoutManager(layoutManager);
        tableAdapter = new TableAdapter(this, table);
        tableAdapter.setOnItemClickListener(new TableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TableDomain table) {
                // Получение даты и времени из выбранных значений
                String day = getSelectedDate();
                String time = getSelectedTime();
                String number = table.getTitle();

                String name = "Your Name";

                // Выполнение POST-запроса на бронирование стола
                executePostRequest(day, time, number, name);
            }
        });
        tableRecycler.setAdapter(tableAdapter);
        tableRecycler.smoothScrollToPosition(0);
        tableRecycler.setHasFixedSize(true);
    }

    public void showTimePickerDialog() {
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setOnTimeSetListener(new TimePickerFragment.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Обновление выбранного времени
                updateTime(hourOfDay, minute);
            }
        });
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog() {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setOnDateSetListener(new DatePickerFragment.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Обновление выбранной даты
                updateDate(year, month, dayOfMonth);
            }
        });
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

    private void executePostRequest(String day, String time, String number, String name) {
        RequestQueue queue = Volley.newRequestQueue(this);

        // Создание JSON-объекта с необходимыми данными
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("day", day);
            jsonBody.put("time", time);
            jsonBody.put("number", number);
            jsonBody.put("name", name);
            jsonBody.put("price", price);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        // Создание POST-запроса
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "https://losermaru.pythonanywhere.com/reservation", jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Обработка успешного ответа от сервера
                        Toast.makeText(BookingActivity2.this, "Reservation successful", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Обработка ошибки запроса
                        Toast.makeText(BookingActivity2.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(request);
    }

    private String getSelectedDate() {
        // Получение выбранной даты из DatePickerFragment
        DatePickerFragment datePickerFragment = (DatePickerFragment) getSupportFragmentManager().findFragmentByTag("datePicker");
        if (datePickerFragment != null) {
            return datePickerFragment.getSelectedDate();
        }
        return "";
    }

    private String getSelectedTime() {
        // Получение выбранного времени из TimePickerFragment
        TimePickerFragment timePickerFragment = (TimePickerFragment) getSupportFragmentManager().findFragmentByTag("timePicker");
        if (timePickerFragment != null) {
            return timePickerFragment.getSelectedTime();
        }
        return "";
    }

    private void updateDate(int year, int month, int dayOfMonth) {
        // Обновление выбранной даты в DatePickerFragment
        DatePickerFragment datePickerFragment = (DatePickerFragment) getSupportFragmentManager().findFragmentByTag("datePicker");
        if (datePickerFragment != null) {
            datePickerFragment.updateDate(year, month, dayOfMonth);
        }
    }

    private void updateTime(int hourOfDay, int minute) {
        // Обновление выбранного времени в TimePickerFragment
        TimePickerFragment timePickerFragment = (TimePickerFragment) getSupportFragmentManager().findFragmentByTag("timePicker");
        if (timePickerFragment != null) {
            timePickerFragment.updateTime(hourOfDay, minute);
        }
    }
}
