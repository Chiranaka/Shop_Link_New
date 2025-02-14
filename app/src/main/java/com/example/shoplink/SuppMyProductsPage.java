package com.example.shoplink;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Handler;

public class SuppMyProductsPage extends AppCompatActivity {

    private Handler handler;
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



        // Create a handler to handle UI updates from the background thread
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                // Update the TextView with the current count value
                EventChangeListener();
//                recyclerView = findViewById(R.id.recyclerViewOrders);
//                recyclerView.setHasFixedSize(true);
//                recyclerView.setLayoutManager(new LinearLayoutManager(context));
//
//                productList = new ArrayList<>();
//                productadapter = new ProductAdapter(context, productList);
//                recyclerView.setAdapter(productadapter);

                return true;
            }
        });

        // Start a background thread to refresh the UI
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        // Simulate some work being done
                        Thread.sleep(1500); // Delay for 1 second


                        // Post a message to the handler to update the UI
                        handler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();





        SharedPreferences sharedPreferences = getSharedPreferences("Prefs", MODE_PRIVATE);
        String sn = sharedPreferences.getString("supplierName", "Not Found");

        TextView messageView = (TextView)findViewById(R.id.txtSuppName);
        messageView.setText("Welcome: " + sn);

        db = FirebaseFirestore.getInstance();


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

//        Toast.makeText(this, "Welcome: " + sn, Toast.LENGTH_LONG).show();
        //******************************************************************************************
        //Recycler view data view codes
        recyclerView = findViewById(R.id.recyclerViewOrders);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        productList = new ArrayList<ModelProduct>();
        productadapter = new ProductAdapter(SuppMyProductsPage.this,productList);

        recyclerView.setAdapter(productadapter);




        //******************************************************************************************

        //set color to footer image buttons
        ImageButton imgBtnToProducts = findViewById(R.id.imgBtnToProducts);
        imgBtnToProducts.setBackground(new ColorDrawable(Color.parseColor("#7FC7D9")));

        //******************************************************************************************



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






        /*// Set up the RecyclerView
        recyclerView = findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();
        adapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        fetchProductsFromFirestore();*/
    }

//    private void EventChangeListener() {
//        db.collection("products")
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        if(error != null)
//                        {
//                            if(progressDialog.isShowing())
//                                progressDialog.dismiss();
//                            Log.e("Firestore error ",error.getMessage());
//                            return;
//                        }
//
//                        for(DocumentChange dc : value.getDocumentChanges()){
//
//                            if(dc.getType() == DocumentChange.Type.ADDED){
//                                productList.add(dc.getDocument().toObject(ModelProduct.class));
//                            }
//
//                            productadapter.notifyDataSetChanged();
//                            if(progressDialog.isShowing())
//                                progressDialog.dismiss();
//                        }
//                    }
//                });
//    }

//    private void EventChangeListener() {
//        db.collection("products_map")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            productList.clear(); // Clear the list before adding new data
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                // Retrieve the document data as a map
//                                Map<String, Object> data = document.getData();
//
//                                // Manually extract fields from the map.
//                                // For example, if ModelProduct has fields: id, name, price, and description:
//
//                                String data1 = data.get("1") != null ? data.get("1").toString() : "";
//                                String data2 = data.get("2") != null ? data.get("2").toString() : "";
//                                String data3 = data.get("3") != null ? data.get("3").toString() : "";
//                                String data4 = data.get("4") != null ? data.get("4").toString() : "";
//                                String data5 = data.get("5") != null ? data.get("5").toString() : "";
//                                String data6 = data.get("6") != null ? data.get("6").toString() : "";
//                                String data7 = data.get("7") != null ? data.get("7").toString() : "";
//                                String data8 = data.get("8") != null ? data.get("8").toString() : "";
//                                String data9 = data.get("9") != null ? data.get("9").toString() : "";
//                                String data10 = data.get("10") != null ? data.get("10").toString() : "";
//
//                                // Create a new ModelProduct object using a custom constructor or setters
//                                ModelProduct product = new ModelProduct(data1,data2,data3,data4,data5,data6,data7,data8,data9,data10);
//                                // Optionally, set the document ID if needed
//                                product.setId(document.getId());
//
//                                productList.add(product);
//                            }
//                            // Notify the adapter that the data has changed
//                            productadapter.notifyDataSetChanged();
//                            Log.d(TAG, "Products fetched successfully. List size: " + productList.size());
//                            progressDialog.hide();
//                            Toast.makeText(SuppMyProductsPage.this,"Products fetched successfully. List size: " + productList.size() , Toast.LENGTH_SHORT).show();
//
//
//                        } else {
//                            Toast.makeText(SuppMyProductsPage.this, "Failed to load products: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                            Log.e(TAG, "Failed to load products: ", task.getException());
//                        }
//                    }
//                });
//    }
    private void EventChangeListener() {
        db.collection("products") // Use the correct collection name
                .addSnapshotListener(new EventListener<QuerySnapshot>() { // Use addSnapshotListener for real-time updates
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("Firestore error ", error.getMessage());
                            return;
                        }

                        productList.clear(); // Clear the list *before* adding new data
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                ModelProduct product = dc.getDocument().toObject(ModelProduct.class);
                                productList.add(product);
                            }
                        }

                         //Notify the adapter that the data has changed
                            productadapter.notifyDataSetChanged();
                            Log.d(TAG, "Products fetched successfully. List size: " + productList.size());
                            progressDialog.hide();
                    }
                });
    }
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

