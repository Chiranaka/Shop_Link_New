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
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SuppMyProductsPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private ArrayList<ModelProduct> productList;
    private FirebaseFirestore db;
    private ProgressDialog progressDialog;
    private Context context;
    private ListenerRegistration productListener; // To manage Firestore listener

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

        // Set footer button color
        ImageButton imgBtnToProducts = findViewById(R.id.imgBtnToProducts);
        imgBtnToProducts.setBackground(new ColorDrawable(Color.parseColor("#7FC7D9")));

        // Retrieve supplier name from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("Prefs", MODE_PRIVATE);
        String sn = sharedPreferences.getString("supplierName", "Guest");

        TextView messageView = findViewById(R.id.txtSuppName);
        messageView.setText("Welcome: " + sn);

        context = this;

        // Button click listeners
        findViewById(R.id.btnAddNewProduct).setOnClickListener(view ->
                startActivity(new Intent(context, SuppAddNewProductPage.class)));

        findViewById(R.id.imgBtnToUserAcc).setOnClickListener(view ->
                startActivity(new Intent(context, SupplierUserProfileView.class)));

        findViewById(R.id.imgBtnToProducts).setOnClickListener(view -> {
            startActivity(new Intent(context, SuppMyProductsPage.class));
            finish();
        });

        findViewById(R.id.imgBtnToOrderList).setOnClickListener(view -> {
            startActivity(new Intent(context, SuppReceivedAcceptedOrderPage.class));
            finish();
        });

        findViewById(R.id.imgBtnLogout).setOnClickListener(view -> {
            SharedPreferences sp = getSharedPreferences("Prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(context, LogInPage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // RecyclerView setup
        recyclerView = findViewById(R.id.recyclerViewOrders);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(SuppMyProductsPage.this, productList);
        recyclerView.setAdapter(productAdapter);

        db = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        fetchProductsFromFirestore();
    }

    private void fetchProductsFromFirestore() {
        productListener = db.collection("products")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("Firestore Error", error.getMessage());
                            if (progressDialog.isShowing()) progressDialog.dismiss();
                            return;
                        }

                        if (value == null) {
                            if (progressDialog.isShowing()) progressDialog.dismiss();
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            ModelProduct product = dc.getDocument().toObject(ModelProduct.class);
                            switch (dc.getType()) {
                                case ADDED:
                                    productList.add(product);
                                    break;
                                case MODIFIED:
                                    // Find existing product and update it
                                    int index = -1;
                                    for (int i = 0; i < productList.size(); i++) {
                                        if (productList.get(i).getProductId().equals(product.getProductId())) {
                                            index = i;
                                            break;
                                        }
                                    }
                                    if (index != -1) {
                                        productList.set(index, product);
                                    }
                                    break;
                                case REMOVED:
                                    // Remove the product from the list
                                    productList.removeIf(p -> p.getProductId().equals(product.getProductId()));
                                    break;
                            }
                        }

                        runOnUiThread(() -> {
                            productAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        });
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Remove Firestore listener when activity stops
        if (productListener != null) {
            productListener.remove();
        }
        // Dismiss progress dialog to prevent leaks
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}