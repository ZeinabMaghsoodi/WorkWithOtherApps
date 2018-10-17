package com.gamecodeschool.workwithotherapps;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView;
    Button btnTakePhoto, btnTurnLocation, btnTurnWifi, btnTurnBluetooth;
    TextView txtLocation, txtWifi, txtBluetooth;
    Boolean locationResult, wifiResult, blutoothResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindWidgets();

        btnTakePhoto.setOnClickListener(this);
        btnTurnLocation.setOnClickListener(this);
        btnTurnWifi.setOnClickListener(this);
        btnTurnBluetooth.setOnClickListener(this);
    }

    protected boolean isLocationEnabled() {
        String le = Context.LOCATION_SERVICE;
        LocationManager locationManager = (LocationManager) getSystemService(le);
        if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            return false;
        } else {
            return true;
        }
    }

    protected boolean isWifiEnabled() {
        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled()) {
            return true;
        }
        return false;
    }

    protected boolean isBluetoothEnabled() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Log.d("Bluetooth", "isBluetoothEnabled: Does not support bluetooth.");
            return false;
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                return false;
            }
            return true;
        }
    }

    public void getPermission() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("PLAYGROUND", "Permission is not granted, requesting");
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        } else {
            Log.d("PLAYGROUND", "Permission is granted");
        }
    }

    public void bindWidgets() {
        imageView = findViewById(R.id.imageView);
        btnTakePhoto = findViewById(R.id.btnTakePhoto);
        btnTurnLocation = findViewById(R.id.btnTurnLocation);
        btnTurnWifi = findViewById(R.id.btnTurnWifi);
        btnTurnBluetooth = findViewById(R.id.btnTurnBluetooth);
        txtLocation = findViewById(R.id.txtLocation);
        txtWifi = findViewById(R.id.txtWifi);
        txtBluetooth = findViewById(R.id.txtBluetooth);

        if (isLocationEnabled())
            txtLocation.setText("Location is ON");
        else {
            txtLocation.setText("Location is OFF");
        }

        if (isWifiEnabled())
            txtWifi.setText("Wifi is ON");
        else {
            txtWifi.setText("Wifi is OFF");
        }

        if (isBluetoothEnabled())
            txtBluetooth.setText("Bluetooth is ON");
        else {
            txtBluetooth.setText("Bluetooth is OFF");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnTakePhoto) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                Log.d("Camera", "Permission is not granted, requesting");
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 123);
            }
            Intent photo = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivityForResult(photo, 100);
        } else if (v.getId() == R.id.btnTurnLocation) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.d("Location", "Permission is not granted, requesting");
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
            }
            Intent locationIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(locationIntent, 200);
        } else if (v.getId() == R.id.btnTurnWifi) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
                Log.d("Wifi", "Permission is not granted, requesting");
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
            }
            Intent locationIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            startActivityForResult(locationIntent, 300);
        } else if (v.getId() == R.id.btnTurnBluetooth) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
                Log.d("BLUETOOTH", "Permission is not granted, requesting");
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
            }
            Intent bluetoothIntent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
            startActivityForResult(bluetoothIntent, 400);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case 100:
                    if (resultCode == RESULT_OK) {
                        Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                        imageView.setImageBitmap(imageBitmap);
                    } else if (resultCode == RESULT_CANCELED) {
                        Toast.makeText(this, "Take Photo Cancled", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 200:
                    locationResult = isLocationEnabled();
                    if (locationResult == true) {
                        txtLocation.setText("Location is ON");
                    } else {
                        txtLocation.setText("Location is OFF");
                    }
                    break;
                case 300:
                    wifiResult = isWifiEnabled();
                    if (wifiResult == true) {
                        txtWifi.setText("Wifi is ON");
                    } else {
                        txtWifi.setText("Wifi is OFF");
                    }
                    break;
                case 400:
                    blutoothResult = isBluetoothEnabled();
                    if (blutoothResult == true) {
                        txtBluetooth.setText("Bluetooth is ON");
                    } else {
                        txtBluetooth.setText("Bluetooth is OFF");
                    }
                    break;
            }
        } catch (Exception e)

        {
            Log.d("onActivityResult", "onActivityResult: " + e.toString());
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}

