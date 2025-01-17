package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {

    // variables
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    Button add_city;
    Button delete_city;
    Button confirm_city;

    EditText city_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // access ListView using its id
        cityList = findViewById(R.id.city_list);

        String []cities = {"Edmonton","Calgary","Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        add_city = findViewById(R.id.add_city);
        delete_city = findViewById(R.id.delete_city);

        confirm_city = findViewById(R.id.confirm_city);
        city_input = findViewById(R.id.user_input);

        city_input.setVisibility(View.GONE); // Hides the enter city text box
        confirm_city.setVisibility(View.GONE); // Hides confirm button

        // when add city button is clicked the textbox and confirm button
        // appear at the bottom of the screen
        // type in the city and hit confirm
        // the textbox and confirm button is gone
        add_city.setOnClickListener(v -> {
            city_input.setVisibility(View.VISIBLE);
            confirm_city.setVisibility(View.VISIBLE);
            confirm_city.setOnClickListener(c -> {
                String new_city = city_input.getText().toString();
                dataList.add(new_city);
                cityAdapter.notifyDataSetChanged();
                city_input.setVisibility(View.GONE);
                confirm_city.setVisibility(View.GONE);
            });
        });

        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String select_city;
                // once user clicks on a city they want to remove
                // we get that position of the clicked city
                // and remove from the list
                select_city = dataList.get(position);
                delete_city.setOnClickListener(v ->{
                    dataList.remove(select_city);
                    cityAdapter.notifyDataSetChanged();
                });
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}