package com.example.shoplink;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SuppReceivedAcceptedOrderPage extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_supp_received_accepted_order_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //******************************************************************************************

        //set color to footer image buttons
        ImageButton imgBtnToOrderList = findViewById(R.id.imgBtnToOrderList);
        imgBtnToOrderList.setBackground(new ColorDrawable(Color.parseColor("#7FC7D9")));

        //******************************************************************************************

        //******************************************************************************************

        context = this;

        //******************************************************************************************
        findViewById(R.id.imgBtnToUserAcc).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, SupplierUserProfileView.class));
            }
        });

        findViewById(R.id.imgBtnToMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        findViewById(R.id.imgBtnToChat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        findViewById(R.id.imgBtnToProducts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, SuppMyProductsPage.class));
                finish();
            }
        });

        findViewById(R.id.imgBtnToOrderList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, SuppReceivedAcceptedOrderPage.class));
                finish();
            }
        });

        findViewById(R.id.imgBtnToNotifications).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}