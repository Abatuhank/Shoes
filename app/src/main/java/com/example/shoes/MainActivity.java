package com.example.shoes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.shoes.database.ShoeDatabaseHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ShoeDatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        ArrayList<Shoe> shoeList = getIntent().getParcelableArrayListExtra("shoeList");


        ListView lw = findViewById(R.id.listView);
        ShoeAdapter adapter = new ShoeAdapter(MainActivity.this, shoeList);
        lw.setAdapter(adapter);

        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                //Toast.makeText(MainActivity.this, shoeList.get(i).getYouUrl(), Toast.LENGTH_SHORT).show();
                intent.putExtra("shoe",shoeList.get(i));
                startActivity(intent);

            }
        });
    }
}