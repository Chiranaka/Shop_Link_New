package com.example.shoplink;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.integration.android.IntentIntegrator;


public class SuppAddNewProductPage extends AppCompatActivity {

    private EditText productName, supplyPrize, maxSellingPrize, ShipFeePerOrder, productQuality, miniQuantity, maxQuantity, description, imageUrl, productCode;
    Context context;
    private FirebaseFirestore db;
    private ImageView imgProduct;
    private Button btntoPickProdImage;
    private Uri imageUri;
    private StorageReference storageRef;
    private static final int PICK_IMAGE_REQ = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

//        SharedPreferences sharedPreferences = getSharedPreferences("suplierPrefs", MODE_PRIVATE);
//        String m1 = sharedPreferences.getString("m1", "");
//        String m2 = sharedPreferences.getString("m2", "");
//        String m3 = sharedPreferences.getString("m3", "");
//        String m4 = sharedPreferences.getString("m4", "");
//        String m5 = sharedPreferences.getString("m5", "");
//        String m6 = sharedPreferences.getString("m6", "");
//        String m7 = sharedPreferences.getString("m7", "");
//        String m8 = sharedPreferences.getString("m8", "");
//        String m9 = sharedPreferences.getString("m9", "");
//        String m10 = sharedPreferences.getString("m10", "");
//
//        productName = (EditText) findViewById(R.id.edtTxtProductName);
//        productName.setText(m1);
//
//        supplyPrize = (EditText) findViewById(R.id.edtTxtSupplyPrice);
//        supplyPrize.setText(m2);
//
//        maxSellingPrize = (EditText) findViewById(R.id.edtTxtSellingPrice);
//        maxSellingPrize.setText(m3);
//
//        ShipFeePerOrder = (EditText) findViewById(R.id.edtTxtShipFeePerOrder);
//        ShipFeePerOrder.setText(m4);
//
//        productQuality = (EditText) findViewById(R.id.edtTxtAddress);
//        productQuality.setText(m5);
//
//        miniQuantity = (EditText) findViewById(R.id.edtTxtMinQty);
//        miniQuantity.setText(m6);
//
//        maxQuantity = (EditText) findViewById(R.id.edtTxtMaxQty);
//        maxQuantity.setText(m7);
//
//        description = (EditText)findViewById(R.id.edtTxtProductDescription);
//        description.setText(m8);
//
////        imgProduct  = findViewById(R.id.imgProduct);
////        imgProduct.setText(messageName);
//
//        productCode = (EditText)findViewById(R.id.edtTxtProdCode);
//        if(productCode.toString().isEmpty() == true){
//            productCode.setText(m10);
//        }



//        BarCodeScanner QR = new BarCodeScanner(context);
//
//        TextInputEditText editText = findViewById(R.id.edtTxtProdCode);
//
//
//
//        new Thread(() -> {
//            String value;
//            do {
//                value = QR.getValue();
//            } while (value.isEmpty());
//
//            String finalValue = value;
//            runOnUiThread(() -> editText.setText(finalValue));
//        }).start();


        setContentView(R.layout.activity_supp_add_new_product_page);

        Intent intent = getIntent();
        String messageName = intent.getStringExtra("Scanned_code");
        TextInputEditText messageView = findViewById(R.id.edtTxtProdCode);
        messageView.setText(messageName);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




        db = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference("Product_images");


        productName = (EditText) findViewById(R.id.edtTxtProductName);
        supplyPrize = (EditText) findViewById(R.id.edtTxtSupplyPrice);
        maxSellingPrize = (EditText) findViewById(R.id.edtTxtSellingPrice);
        ShipFeePerOrder = (EditText) findViewById(R.id.edtTxtShipFeePerOrder);
        productQuality = (EditText) findViewById(R.id.edtTxtAddress);
        miniQuantity = (EditText) findViewById(R.id.edtTxtMinQty);
        maxQuantity = (EditText) findViewById(R.id.edtTxtMaxQty);
        description = (EditText)findViewById(R.id.edtTxtProductDescription);
        imgProduct  = findViewById(R.id.imgProduct);
        productCode = (EditText)findViewById(R.id.edtTxtProdCode);


        btntoPickProdImage = findViewById(R.id.btntoPickProdImage);


        context = this;

        btntoPickProdImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        findViewById(R.id.btnAddNewProduct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduct();

                Intent intent = new Intent(SuppAddNewProductPage.this, SuppMyProductsPage.class);
                startActivity(intent);
            }
        });




        findViewById(R.id.imgBtnProdQr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent qintent = new Intent(SuppAddNewProductPage.this, QrScanner.class);
//                startActivity(qintent);

//                SharedPreferences sp = getSharedPreferences("suplierPrefs", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sp.edit();
//                editor.putString("m1", productName.toString() );
//                editor.putString("m2", supplyPrize.toString());
//                editor.putString("m3", maxSellingPrize.toString());
//                editor.putString("m4", ShipFeePerOrder.toString());
//                editor.putString("m5", productQuality.toString());
//                editor.putString("m6", miniQuantity.toString() );
//                editor.putString("m7", maxQuantity.toString());
//                editor.putString("m8", description.toString());
//                editor.putString("m9", imgProduct.toString());
//                editor.putString("m10", productCode.toString());
//                editor.apply();

//                BarCodeScanner QR = new BarCodeScanner(context);
//                QR.startScan();

                findViewById(R.id.imgBtnProdQr).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Create a new BarCodeScanner instance
                        BarCodeScanner scanner = new BarCodeScanner(SuppAddNewProductPage.this);
                        // Start the scan and handle the result in the callback
                        scanner.startScan(new OnBarcodeScannedListener() {
                            @Override
                            public void onBarcodeScanned(String scannedValue) {
                                TextInputEditText editText = findViewById(R.id.edtTxtProdCode);
                                editText.setText(scannedValue);
                            }
                        });
                    }
                });

                SharedPreferences sharedPreferences = getSharedPreferences("Prefs0", MODE_PRIVATE);
                String pc = sharedPreferences.getString("productCode", "Not Found");

            }
        });

    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQ);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQ && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();

            imgProduct.setImageURI(imageUri); // show selected image
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
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
//      String SimageUrl = imageUrl.getText().toString();
        String SproductCode = productCode.getText().toString();

        if (SproductName.isEmpty() || SsupplyPrize.isEmpty() || SmaxSellingPrize.isEmpty() ||
                SShipFeePerOrder.isEmpty() || SproductQuality.isEmpty() ||
                SminiQuantity.isEmpty() || SmaxQuantity.isEmpty() || Sdescription.isEmpty()) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (imageUri == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        String productId = db.collection("products").document().getId().toString();

        // Create a storage reference with a unique name based on current time and file extension
//        final StorageReference fileReference = storageRef.child(System.currentTimeMillis()
//                + "." + getFileExtension(imageUri));

        if (imageUri != null) {
            imgProduct.setImageURI(imageUri); // Display selected image
            Toast.makeText(this, "Image Selected: " + imageUri.toString(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error: Image not found!", Toast.LENGTH_SHORT).show();
        }

        String fileExtension = getFileExtension(imageUri);

        if (fileExtension == null) {
            Toast.makeText(this, "Invalid file type!", Toast.LENGTH_SHORT).show();
            return;
        }

        String fileName = System.currentTimeMillis() + "." + fileExtension;
        StorageReference fileReference = storageRef.child(fileName);

        Toast.makeText(this, "Uploading: " + fileReference.getPath(), Toast.LENGTH_SHORT).show();


        // Upload the image to Firebase Storage
        fileReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Retrieve the download URL once the image is uploaded
                        fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(getApplicationContext(), "Image Upload successful!", Toast.LENGTH_SHORT).show();
                                String downloadUrl = uri.toString();

                                // Create the product model with the image download URL
                                ModelProduct modelProduct = new ModelProduct(
                                        SproductName,
                                        SsupplyPrize,
                                        SmaxSellingPrize,
                                        SShipFeePerOrder,
                                        SproductQuality,
                                        SminiQuantity,
                                        SmaxQuantity,
                                        Sdescription,
                                        downloadUrl,
                                        SproductCode
                                );

                                // Save the product data in Firestore
                                db.collection("products")
                                        .document(productId)
                                        .set(modelProduct)
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(context, "Product added successfully!", Toast.LENGTH_SHORT).show();
                                        })                                        .addOnFailureListener(e -> {
                                            Toast.makeText(context, "Error adding product: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        });
                            }
                        }).addOnFailureListener(e -> {
                            Toast.makeText(context, "Error retrieving download URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Image upload failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });





    }
}
