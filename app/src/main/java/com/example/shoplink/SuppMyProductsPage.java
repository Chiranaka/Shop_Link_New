package com.example.shoplink;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SuppMyProductsPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productadapter;
    private ArrayList<ModelProduct> productList;
    private FirebaseFirestore db;
    ProgressDialog progressDialog;

    private Context context;

//    private void messegePass(String messageName) {
//
//        Intent intent_ = new Intent(this, SupplierHeader.class);
//        intent_.putExtra("messageName", messageName);
//        startActivity(intent_);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_supp_my_products_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //******************************************************************************************

        //set color to footer image buttons
        ImageButton imgBtnToProducts = findViewById(R.id.imgBtnToProducts);
        imgBtnToProducts.setBackground(new ColorDrawable(Color.parseColor("#7FC7D9")));

        //******************************************************************************************

        SharedPreferences sharedPreferences = getSharedPreferences("Prefs", MODE_PRIVATE);
        String sn = sharedPreferences.getString("supplierName", "Not Found");

        TextView messageView = (TextView)findViewById(R.id.txtShopName);
        messageView.setText("Welcome: " + sn);

//        Toast.makeText(this, "Welcome: " + sn, Toast.LENGTH_LONG).show();

//        Intent intent = getIntent();
//
//            String messageName = intent.getStringExtra("messageName");
//
//            messegePass(messageName);

        //******************************************************************************************


        context = this;


        //******************************************************************************************

        findViewById(R.id.btnAddNewProduct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, SuppAddNewProductPage.class));
            }
        });

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

        findViewById(R.id.imgBtnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear saved login session
                SharedPreferences sp = getSharedPreferences("Prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear(); // Remove all saved data
                editor.apply();

                // Redirect to Login Activity
                Intent intent = new Intent(context, LogInPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear activity stack
                startActivity(intent);
                finish(); // Close current activity
            }
        });


        //******************************************************************************************
        //Recycler view data view codes
        recyclerView = findViewById(R.id.recyclerViewOrders);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        productList = new ArrayList<ModelProduct>();
        productadapter = new ProductAdapter(SuppMyProductsPage.this,productList);

        recyclerView.setAdapter(productadapter);

        EventChangeListener();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();



        /*// Set up the RecyclerView
        recyclerView = findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();
        adapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        fetchProductsFromFirestore();*/
    }

    private void EventChangeListener() {
        db.collection("products")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null)
                        {
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("Firestore error",error.getMessage());
                            return;
                        }

                        for(DocumentChange dc : value.getDocumentChanges()){

                            if(dc.getType() == DocumentChange.Type.ADDED){
                                productList.add(dc.getDocument().toObject(ModelProduct.class));
                            }

                            productadapter.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }
                });
    }
    /*private void fetchProductsFromFirestore() {
        CollectionReference productsRef = db.collection("Products");

        productsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                productList.clear(); // Clear existing data

                for (QueryDocumentSnapshot document : task.getResult()) {
                    ModelProduct product = document.toObject(ModelProduct.class);
                    productList.add(product);
                }

                adapter.notifyDataSetChanged(); // Refresh RecyclerView
            } else {
                Toast.makeText(SuppMyProductsPage.this, "Failed to load products", Toast.LENGTH_SHORT).show();
            }
        });




    }*/
}
