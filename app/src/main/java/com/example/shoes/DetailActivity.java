package com.example.shoes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoes.R;
import com.example.shoes.database.ShoeDatabaseHelper;

public class DetailActivity extends AppCompatActivity {
    private ShoeDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Shoe shoe = getIntent().getParcelableExtra("shoe");

        //Toast.makeText(this,  shoe.getName(), Toast.LENGTH_SHORT).show();
        // Intent ile gelen verileri al
        String name = shoe.getName();
        int size = shoe.getSize();
        double price =shoe.getPrice();
        String image = shoe.getImageUrl();
        String youUrl = "https://www.youtube.com/embed/" + shoe.getYouUrl();
        //Toast.makeText(this, youUrl, Toast.LENGTH_SHORT).show();


        // XML öğelerini bağla
        TextView nameTextView = findViewById(R.id.detail_name);
        TextView sizeTextView = findViewById(R.id.detail_size);
        TextView priceTextView = findViewById(R.id.detail_price);
        WebView youPlayer = findViewById(R.id.youPlayer);
        WebSettings webSettings = youPlayer.getSettings();
        webSettings.setJavaScriptEnabled(true);
        Button btn_youtube = findViewById(R.id.btn_youtube);

        youPlayer.setWebViewClient(new WebViewClient());
        youPlayer.loadUrl(youUrl);

        //Toast.makeText(this, youUrl, Toast.LENGTH_SHORT).show();

        btn_youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String youtubeUrl = "https://www.youtube.com/watch?v=" + shoe.getYouUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(youtubeUrl));
                intent.setPackage("com.google.android.youtube");
                startActivity(intent);

            }
        });

        // Verileri göster
        nameTextView.setText(name);
        sizeTextView.setText("Size: " + size);
        priceTextView.setText("Price: $" + price);

    }
}
