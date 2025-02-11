package com.example.shoplink;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;

public class ShopSignUpPage extends AppCompatActivity {

    private EditText email, ownerName, shopBusinessName, contactNo, address, shopRegNo, description, password, confirmPassword;
    private TextView linkToLogin;
    private Button register;
    FirebaseFirestore firestore;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shop_sign_up_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        // Link UI components
        email = (EditText)findViewById(R.id.edtTxtEmail);
        ownerName = (EditText)findViewById(R.id.edtTxtOwnerName);
        shopBusinessName = (EditText)findViewById(R.id.edtTxtShopName);
        contactNo = (EditText)findViewById(R.id.edtTxtContactNo);
        address = (EditText)findViewById(R.id.edtTxtShopAddress);
        shopRegNo = (EditText)findViewById(R.id.edtTxtShopRegNo);
        description = (EditText)findViewById(R.id.edtTxtShopDescription);
        password = (EditText)findViewById(R.id.edtTxtPassword);
        confirmPassword = (EditText)findViewById(R.id.edtTxtConfirmPassword);
        register = findViewById(R.id.btnShopRegister);
        linkToLogin = findViewById(R.id.txtLinkToLogIn);
        context = this;

        linkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, LogInPage.class));
            }
        });

        // Button click event to save data
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addShop();
            }
        });
    }

    private void addShop()
    {
        // Get user input values
        String shopEmail = email.getText().toString();
        String shopOwnerName = ownerName.getText().toString();
        String shopName = shopBusinessName.getText().toString();
        String shopContNo = contactNo.getText().toString();
        String shopAddress = address.getText().toString();
        String shopBusinessRegNo = shopRegNo.getText().toString();
        String shopDescription = description.getText().toString();
        String shopPassword = password.getText().toString();
        String shopConfPassword = confirmPassword.getText().toString();

        if(shopEmail.isEmpty() || shopOwnerName.isEmpty() || shopName.isEmpty() ||
                shopContNo.isEmpty() ||shopAddress.isEmpty() || shopBusinessRegNo.isEmpty() ||
                shopDescription.isEmpty() || shopPassword.isEmpty() || shopConfPassword.isEmpty())
        {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            if (shopPassword == shopConfPassword)
            {
                // Generate a unique shop ID
                String shopId = firestore.collection("shop").document().getId();

                // Create shop object
                ModelShop modelshop = new ModelShop(shopId, shopEmail, shopOwnerName,
                        shopName, shopContNo, shopAddress,
                        shopBusinessRegNo, shopDescription, shopPassword);


                // Store data in Firestore under "shop" collection
                firestore.collection("shop").document(shopId)
                        .set(modelshop)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(ShopSignUpPage.this, "Shop account created successfully!", Toast.LENGTH_SHORT).show();
                            // Clear input fields
                            email.setText("");
                            ownerName.setText("");
                            shopBusinessName.setText("");
                            contactNo.setText("");
                            address.setText("");
                            shopRegNo.setText("");
                            description.setText("");
                            password.setText("");
                            confirmPassword.setText("");

                        })
                        .addOnFailureListener(e -> {
                            Log.e("Firestore", "Error adding shop", e);
                            Toast.makeText(ShopSignUpPage.this, "Failed to create shop acoount!", Toast.LENGTH_SHORT).show();
                        });


            }
            else
            {
                Toast.makeText(ShopSignUpPage.this, "Passwords are not matched !", Toast.LENGTH_SHORT).show();
            }

        }

    }
}