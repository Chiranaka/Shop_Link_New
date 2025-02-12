package com.example.shoplink;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;


public class SuppAddNewProductPage extends AppCompatActivity {

    private EditText productName, supplyPrize, maxSellingPrize, ShipFeePerOrder, productQuality, miniQuantity, maxQuantity, description, imageUrl;
    Context context;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_supp_add_new_product_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();

        productName = (EditText) findViewById(R.id.edtTxtProductName);
        supplyPrize = (EditText) findViewById(R.id.edtTxtSupplyPrice);
        maxSellingPrize = (EditText) findViewById(R.id.edtTxtSellingPrice);
        ShipFeePerOrder = (EditText) findViewById(R.id.edtTxtShipFeePerOrder);
        productQuality = (EditText) findViewById(R.id.edtTxtAddress);
        miniQuantity = (EditText) findViewById(R.id.edtTxtMinQty);
        maxQuantity = (EditText) findViewById(R.id.edtTxtMaxQty);
        description = findViewById(R.id.edtTxtProductDescription);

        context = this;

        findViewById(R.id.btnAddNewProduct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduct();
            }
        });

    }


    private void addProduct(){

        // Get user input values
        String SproductName = productName.getText().toString();
        String SsupplyPrize = supplyPrize.getText().toString();
        String SmaxSellingPrize = maxSellingPrize.getText().toString();
        String SShipFeePerOrder = ShipFeePerOrder.getText().toString();
        String SproductQuality = productQuality.getText().toString();
        String SminiQuantity = miniQuantity.getText().toString();
        String SmaxQuantity = maxQuantity.getText().toString();
        String Sdescription = description.getText().toString();
        String SimageUrl = imageUrl.getText().toString();

        if (SproductName.isEmpty() || SsupplyPrize.isEmpty() || SmaxSellingPrize.isEmpty() ||
                SShipFeePerOrder.isEmpty() || SproductQuality.isEmpty() ||
                SminiQuantity.isEmpty() || SmaxQuantity.isEmpty() || Sdescription.isEmpty()) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        String productId = db.collection("products").document().getId();

        ModelProduct modelProduct = new ModelProduct(SproductName, SsupplyPrize, SmaxSellingPrize, SShipFeePerOrder, SproductQuality, SminiQuantity, SmaxQuantity, Sdescription, SimageUrl);
        db.collection("products")
                .document(productId)
                .set(modelProduct)
                .addOnSuccessListener(aVoid ->
                        System.out.println("Product added successfully!"))
                .addOnFailureListener(e ->
                        System.err.println("Error adding product: " + e.getMessage()));

    }
}
