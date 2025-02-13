package com.example.shoplink;

import android.content.Context;
import android.widget.Toast;

import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

public class BarCodeScanner {

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String value;

    private final Context context;

    // Constructor to receive context
    public BarCodeScanner(Context context) {
        this.context = context;
    }

    public void startScan() {
        // Set barcode scanning options
        GmsBarcodeScannerOptions options = new GmsBarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                        Barcode.FORMAT_QR_CODE,
                        Barcode.FORMAT_AZTEC,
                        Barcode.FORMAT_CODABAR,
                        Barcode.FORMAT_CODE_128,
                        Barcode.FORMAT_CODE_39,
                        Barcode.FORMAT_CODE_93,
                        Barcode.FORMAT_DATA_MATRIX,
                        Barcode.FORMAT_EAN_13,
                        Barcode.FORMAT_EAN_8,
                        Barcode.FORMAT_ITF,
                        Barcode.FORMAT_PDF417,
                        Barcode.FORMAT_UPC_A,
                        Barcode.FORMAT_UPC_E
                )
                .enableAutoZoom()
                .build();

        // Create scanner with context
        GmsBarcodeScanner scanner = GmsBarcodeScanning.getClient(context, options);

        // Start scanning
        scanner.startScan()
                .addOnSuccessListener(barcode -> {
                    // Process scanned barcode
                    String scannedValue = barcode.getRawValue();
                    Toast.makeText(context, "QR code detected", Toast.LENGTH_SHORT).show();
                    setValue(scannedValue);
                });

    }
}
