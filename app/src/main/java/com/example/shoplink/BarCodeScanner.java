package com.example.shoplink;

import static android.content.Context.VIBRATOR_MANAGER_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.widget.Toast;

import com.google.android.gms.common.api.OptionalModuleApi;
import com.google.android.gms.common.moduleinstall.InstallStatusListener;
import com.google.android.gms.common.moduleinstall.ModuleInstall;
import com.google.android.gms.common.moduleinstall.ModuleInstallClient;
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest;
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate;
import com.google.android.gms.tflite.java.TfLite;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

public class BarCodeScanner {

    Context context;

    private Vibrator vibrator;

    public void onBarcodeDetected() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { // Android 12 / API 31
            VibratorManager vibratorManager = (VibratorManager) context.getSystemService(context.VIBRATOR_MANAGER_SERVICE);
            if (vibratorManager != null) {
                vibrator = vibratorManager.getDefaultVibrator();
            }
        } else {
            vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        }
//        // Play beep sound
//
       ToneGenerator toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
       toneGen.startTone(ToneGenerator.TONE_PROP_BEEP, 150);
//
        // Trigger vibration
        if (vibrator != null && vibrator.hasVibrator()) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                VibrationEffect effect = VibrationEffect.createOneShot(400, VibrationEffect.DEFAULT_AMPLITUDE);
                vibrator.vibrate(effect);
            } else {
                vibrator.vibrate(400);
            }
        }
    }



    public String getValue() {
        return qrvalue;
    }

    public void setValue(String value) {
        this.qrvalue = value;
    }

    public String qrvalue;


//    static final class ModuleInstallProgressListener implements InstallStatusListener {
//        private Context context;
//        @Override
//        public void onInstallStatusUpdated(ModuleInstallStatusUpdate update) {
//            ModuleInstallStatusUpdate.ProgressInfo progressInfo = update.getProgressInfo();
//            // Progress info is only set when modules are in the progress of downloading.
//            if (progressInfo != null) {
//                int progress =
//                        (int)
//                                (progressInfo.getBytesDownloaded() * 100 / progressInfo.getTotalBytesToDownload());
//                // Set the progress for the progress bar.
//                ProgressDialog p = new ProgressDialog(context);
//                p.setCancelable(false);
//                p.setMessage("Installing model");
//                p.show();
//                p.setProgress(progress);
//            }
//            // Handle failure status maybe…
//
//            // Unregister listener when there are no more install status updates.
//            if (isTerminateState(update.getInstallState())) {
//
//                moduleInstallClient.unregisterListener(this);
//            }
//        }
//
//        public boolean isTerminateState(@InstallState int state) {
//            return state == STATE_CANCELED || state == STATE_COMPLETED || state == STATE_FAILED;
//        }
//    }

    // Constructor to receive context
    public BarCodeScanner(Context context) {
        this.context = context;
    }

    public void startScan(OnBarcodeScannedListener listener) {

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
                    onBarcodeDetected();
                    setValue(scannedValue);

                    listener.onBarcodeScanned(scannedValue);}).addOnFailureListener(e -> {
                    Toast.makeText(context, "Scanning failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    ModuleInstallClient moduleInstallClientnew = ModuleInstall.getClient(context);
                    OptionalModuleApi optionalModuleApi = TfLite.getClient(context);
                    moduleInstallClientnew
                            .areModulesAvailable(optionalModuleApi)
                            .addOnSuccessListener(
                                    response -> {
                                        if (response.areModulesAvailable()) {
                                            // Modules are present on the device...
                                        } else {

                                            OptionalModuleApi optionalModuleApi1 = TfLite.getClient(context);
                                            ModuleInstallRequest moduleInstallRequest =
                                                    ModuleInstallRequest.newBuilder()
                                                            .addApi(optionalModuleApi1)
                                                            // Add more API if you would like to request multiple optional modules
                                                            //.addApi(...)
                                                            // Set the listener if you need to monitor the download progress
                                                            //.setListener(listener)
                                                            .build();
                                            ModuleInstallClient moduleInstallClient = ModuleInstall.getClient(context);
                                            moduleInstallClient.installModules(moduleInstallRequest)
                                                    .addOnSuccessListener(
                                                            response1 -> {
                                                                if (response1.areModulesAlreadyInstalled()) {
                                                                    // Modules are already installed when the request is sent.
                                                                }
                                                            })
                                                    .addOnFailureListener(
                                                            e1 -> {
                                                                // Handle failure...
                                                            });
                                            // Modules are not present on the device...
                                        }
                                    })
                            .addOnFailureListener(
                                    e1 -> {
                                        // Handle failure…
                                    });

//                    ModuleInstallClient moduleInstallClient = ModuleInstall.getClient(context);
//                    ModuleInstallRequest request = ModuleInstallRequest.newBuilder()
//                            .addApi(GmsBarcodeScanning.getClient(context))
//                            .build();
//
//                    moduleInstallClient.installModules(request)
//                            .addOnSuccessListener(response -> {
//                                if (response.areModulesAlreadyInstalled()) {
//                                    scanner.startScan();
//                                } else {
//                                    // Optionally, wait briefly before starting the scan.
//                                    new Handler(Looper.getMainLooper()).postDelayed(() -> startScan(listener), 1000);
//                                }
//                            })
//                            .addOnFailureListener(ee -> {
//                                Toast.makeText(context, "Module install failed: " + ee.getMessage(), Toast.LENGTH_SHORT).show();
//                            });



//                    // Send scanned data to another activity
//                    Intent intent = new Intent(context, SuppAddNewProductPage.class);
//                    intent.putExtra("Scanned_code", scannedValue);
//                    context.startActivity(intent);

                });

    }
}
