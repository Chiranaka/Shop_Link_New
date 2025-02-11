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

import com.google.firebase.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;

public class SupplierSignUpPage extends AppCompatActivity {

    private EditText email, supplierName, businessName, contactNo, address, businessRegNo, description, password, confirmPassword;
    private TextView linkToLogin;
    private Button register;
    FirebaseFirestore firestore;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_supplier_sign_up_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        // Link UI components
        email = (EditText)findViewById(R.id.edtTxtEmail);
        supplierName = (EditText)findViewById(R.id.edtTxtSupplierName);
        businessName = (EditText)findViewById(R.id.edtTxtBusinessName);
        contactNo = (EditText)findViewById(R.id.edtTxtContactNo);
        address = (EditText)findViewById(R.id.edtTxtAddress);
        businessRegNo = (EditText)findViewById(R.id.edtTxtBusinessRegNo);
        description = (EditText)findViewById(R.id.edtTxtSuppDescription);
        password = (EditText)findViewById(R.id.edtTxtPassword);
        confirmPassword = (EditText)findViewById(R.id.edtTxtConfirmPassword);
        register = findViewById(R.id.btnSuppRegister);
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
                addSupplier();
            }
        });
    }

    private void addSupplier()
    {
        // Get user input values
        String supEmail = email.getText().toString();
        String supName = supplierName.getText().toString();
        String supBusinessName = businessName.getText().toString();
        String supContNo = contactNo.getText().toString();
        String supAddress = address.getText().toString();
        String supBusinessRegNo = businessRegNo.getText().toString();
        String supDescription = description.getText().toString();
        String supPassword = password.getText().toString();
        String supConfPassword = confirmPassword.getText().toString();

        if(supEmail.isEmpty() || supName.isEmpty() || supBusinessName.isEmpty() ||
                supContNo.isEmpty() || supAddress.isEmpty() || supBusinessRegNo.isEmpty() ||
                supDescription.isEmpty() || supPassword.isEmpty() || supConfPassword.isEmpty())
        {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            if (supPassword.equals(supConfPassword))
            {
                // Generate a unique supplier ID
                String supplierId = firestore.collection("supplier").document().getId();

                // Create Supplier object
                ModelSupplier modelSupplier = new ModelSupplier(supplierId, supEmail, supName,
                        supBusinessName, supContNo, supAddress,
                        supBusinessRegNo, supDescription, supPassword);


                // Store data in Firestore under "supplier" collection
                firestore.collection("supplier").document(supplierId)
                        .set(modelSupplier)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(SupplierSignUpPage.this, "Supplier account created successfully!", Toast.LENGTH_SHORT).show();
                            // Clear input fields
                            email.setText("");
                            supplierName.setText("");
                            businessName.setText("");
                            contactNo.setText("");
                            address.setText("");
                            businessRegNo.setText("");
                            description.setText("");
                            password.setText("");
                            confirmPassword.setText("");

                        })
                        .addOnFailureListener(e -> {
                            Log.e("Firestore", "Error adding supplier", e);
                            Toast.makeText(SupplierSignUpPage.this, "Failed to create supplier account!", Toast.LENGTH_SHORT).show();
                        });


            }
            else
            {
                Toast.makeText(SupplierSignUpPage.this, "Passwords are not matched !", Toast.LENGTH_SHORT).show();
            }

        }

    }
}