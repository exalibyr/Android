package com.example.excalibur.complexadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> products = new ArrayList<>();
    ProductAdapter adapter;
    ListView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        products.add("ice cream");
        products.add("chocolate");

        view = findViewById(R.id.list);
        adapter = new ProductAdapter(this, R.layout.list_item, products);
        view.setAdapter(adapter);
    }
}
