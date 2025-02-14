package com.example.shoplink;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class SuppMyProductsPage extends AppCompatActivity {

    private Button buttonAddNewProduct;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ArrayList<ModelProduct> productList;
    private FirebaseFirestore db;
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

        SharedPreferences sharedPreferences = getSharedPreferences("Prefs", MODE_PRIVATE);
        String sn = sharedPreferences.getString("supplierName", "Not Found");

        TextView messageView = (TextView)findViewById(R.id.txtSuppName);
        messageView.setText("Welcome: " + sn);

//        Toast.makeText(this, "Welcome: " + sn, Toast.LENGTH_LONG).show();

//        Intent intent = getIntent();
//
//            String messageName = intent.getStringExtra("messageName");
//
//            messegePass(messageName);




        context = this;

        buttonAddNewProduct = findViewById(R.id.btnAddNewProduct);

        buttonAddNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, SuppAddNewProductPage.class));


            }
        });


        // Set up the RecyclerView
        recyclerView = findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();
        adapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        fetchProductsFromFirestore();
    }
    private void fetchProductsFromFirestore() {
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
    }
}