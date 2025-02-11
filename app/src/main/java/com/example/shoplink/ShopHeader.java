package com.example.shoplink;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ShopHeader extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_header);

        Intent intent = getIntent();
        String messageName = intent.getStringExtra("messageName");
        TextView messageView = (TextView)findViewById(R.id.txtShopName);
        messageView.setText(messageName);
    }
}