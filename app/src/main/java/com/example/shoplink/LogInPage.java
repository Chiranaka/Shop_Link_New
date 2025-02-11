package com.example.shoplink;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;



public class LogInPage extends AppCompatActivity {

    private TextView linkToReg;
    private EditText Email, Password;
    private Button buttonLogin;
    private FirebaseFirestore database;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        linkToReg = findViewById(R.id.txtLinkToRegister);
        context = this;
        database = FirebaseFirestore.getInstance();

        Email = findViewById(R.id.edtTxtEmail);
        Password = findViewById(R.id.edtTxtPassword);
        buttonLogin = findViewById(R.id.btnLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LogInPage.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(email, password);
                }
            }
        });

        linkToReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(context, UserSelectionPage.class));
            }
        });
    }

    private void loginUser(String email, String password) {
        database.collection("users").whereEqualTo("email", email).get().addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            String storedPassword = document.getString("password");

                            if (storedPassword != null && storedPassword.equals(password)) {
                                Toast.makeText(LogInPage.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LogInPage.this, MainActivity.class)); // Redirect to HomePage
                                finish();
                            } else {
                                Toast.makeText(LogInPage.this, "Invalid Password!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(LogInPage.this, "User not found!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(LogInPage.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }
}