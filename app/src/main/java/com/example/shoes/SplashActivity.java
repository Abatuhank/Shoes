package com.example.shoes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoes.database.ShoeDatabaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    private ArrayList<Shoe> shoeList; // Firebase'den çekilecek liste
    FirebaseDatabase database;
    DatabaseReference shoesRef;

    private ShoeDatabaseHelper dbHelper; // SQLite bağlantısı

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        dbHelper = new ShoeDatabaseHelper(this);
        shoeList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        shoesRef = database.getReference("Shoes");
        ImageView img = findViewById(R.id.splash_logo);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.loadinganim);
        img.startAnimation(anim);


        // Verileri kontrol et ve yükle
        fetchShoesFromFirebase();
    }

    private void fetchShoesFromFirebase() {
        shoesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Shoe shoe = snapshot.getValue(Shoe.class);
                    shoeList.add(shoe);
                    Toast.makeText(SplashActivity.this, shoe.getYouUrl(), Toast.LENGTH_SHORT).show();

                    // SQLite'a Kaydet
                    dbHelper.getWritableDatabase().execSQL(
                            "INSERT OR REPLACE INTO shoes (name, size, price, image_url, youUrl) VALUES (?, ?, ?, ?, ?)",
                            new Object[]{
                                    shoe.getName(),
                                    shoe.getSize(),
                                    shoe.getPrice(),
                                    shoe.getImageUrl(),
                                    shoe.getYouUrl()
                            }
                    );
                }

                // İşlem tamamlandıktan sonra MainActivity'e geçiş
                moveToMainActivity();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SplashActivity.this, "Veri çekme hatası!", Toast.LENGTH_SHORT).show();
                moveToMainActivity(); // Hata olsa da MainActivity'e geç
            }
        });
    }
    private void moveToMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.putParcelableArrayListExtra("shoeList", shoeList); // Veriyi ekle
        Toast.makeText(this, shoeList.get(0).getYouUrl(), Toast.LENGTH_SHORT).show();
        startActivity(intent);

        finish(); // SplashActivity'i kapat
    }

    private boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
}
