package com.example.ble_try4;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "kamlans";
    private static final int ACCESS_COARSE_LOCATION_REQUEST = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private UUID BLP_SERVICE_UUID = UUID.fromString("00001810-0000-1000-8000-00805f9b34fb");
    private UUID[] serviceUUIDs = new UUID[]{BLP_SERVICE_UUID};
    private String testResult = "";
    private RecyclerView recyclerView;
    private recViewAdapter viewAdapter;

    private BluetoothAdapter adapter;
    private BluetoothLeScanner scanner;
private   List<BT_device> list = new ArrayList<>();

    @Override
    protected void onStart() {
        super.onStart();
        hasPermissions();

        if (!adapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hasPermissions();

        recyclerView = findViewById(R.id.deviceList);
        viewAdapter = new recViewAdapter(getApplicationContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(viewAdapter);

        adapter = BluetoothAdapter.getDefaultAdapter();
        scanner = adapter.getBluetoothLeScanner();
        if (!adapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        List<ScanFilter> filters = null;
        if (serviceUUIDs != null) {
            filters = new ArrayList<>();
            for (UUID serviceUUID : serviceUUIDs) {
                ScanFilter filter = new ScanFilter.Builder()
                        .setServiceUuid(new ParcelUuid(serviceUUID))
                        .build();
                filters.add(filter);
            }
        } else {
            Log.d(TAG, "empty serviceUUID_array");
        }

        ScanSettings scanSettings = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_POWER)
                .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
                .setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE)
                .setNumOfMatches(ScanSettings.MATCH_NUM_ONE_ADVERTISEMENT)
                .setReportDelay(0L)
                .build();

        if (scanner != null) {
            scanner.startScan(filters, scanSettings, scanCallback);
            Log.d(TAG, "scan started");
        } else {
            Log.d(TAG, "could not get scanner object");
        }


        // Get device object for a mac address
//        BluetoothDevice device = adapter.getRemoteDevice(getIntent().getStringExtra("mac"));
//// Check if the peripheral is cached or not
//        int deviceType = device.getType();
//        if (deviceType == BluetoothDevice.DEVICE_TYPE_UNKNOWN) {
//            // The peripheral is not cached
//            Log.d(TAG, "onCreate: not cached peripheral");
//        } else {
//            // The peripheral is cached
//            Log.d(TAG, "onCreate: cached peripheral");
//        }

    }

    private final ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            BluetoothDevice device = result.getDevice();
            // ...do whatever you want with this found device
            testResult = testResult + " " + device.getName();
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            // Ignore for now

            for (ScanResult res : results) {
                Log.d(TAG, "onBatchScanResults: " + res);
                list.add(new BT_device(res.getDevice(), res.getDevice().getName(), res.getDevice().getAddress()));

            }
            viewAdapter.notifyDataSetChanged();



        }

        @Override
        public void onScanFailed(int errorCode) {
            // Ignore for now
            Log.d(TAG, "onScanFailed: " + errorCode);
        }
    };

    private boolean hasPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, ACCESS_COARSE_LOCATION_REQUEST);
                return false;
            }
        }
        return true;
    }


}