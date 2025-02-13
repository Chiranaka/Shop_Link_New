package com.example.shoplink;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
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
        setContentView(R.layout.activity_supp_add_new_product_page);
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
            }
        });



        findViewById(R.id.imgBtnProdQr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent qintent = new Intent(SuppAddNewProductPage.this, QRScannerActivity.class);
                startActivity(qintent);




                SharedPreferences sharedPreferences = getSharedPreferences("Prefs", MODE_PRIVATE);
                String pc = sharedPreferences.getString("productCode", "Not Found");


                addProduct();
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

        String productId = db.collection("products").document().getId();
        // Create a storage reference with a unique name based on current time and file extension
        final StorageReference fileReference = storageRef.child(System.currentTimeMillis()
                + "." + getFileExtension(imageUri));

        // Upload the image to Firebase Storage
        fileReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Retrieve the download URL once the image is uploaded
                        fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
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
                    Toast.makeText(context, "Image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });





    }
}
