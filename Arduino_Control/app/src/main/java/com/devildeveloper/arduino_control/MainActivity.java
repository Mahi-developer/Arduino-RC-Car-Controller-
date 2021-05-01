package com.devildeveloper.arduino_control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Delayed;

public class MainActivity extends AppCompatActivity {

    private TextView status;
    private ListView available_devices;
    private Button bt_on,bt_off,bt_drive;
    private BluetoothAdapter btAdapter;
    private BluetoothDevice btDevice;
    private ArrayList<String> devices = new ArrayList<String>();
    private boolean isEnabled = false;
    private String DeviceName;
    private static BluetoothSocket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Button mov;
    private static boolean isConnected = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hooks
        status = findViewById(R.id.te);
        available_devices = findViewById(R.id.devicesList);
        bt_on = findViewById(R.id.on);
        bt_off = findViewById(R.id.off);
        bt_drive = findViewById(R.id.drive);
        mov = findViewById(R.id.mv);

        //btAdapter
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter != null) {

            //onClick
            bt_on.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    available_devices.setVisibility(View.VISIBLE);
                    if (btAdapter.isEnabled()) {
                        isEnabled = true;
                        startDiscovery();
                        Toast.makeText(MainActivity.this, "Bluetooth is Already in On State", Toast.LENGTH_LONG).show();
                    } else {
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent, 0);
                        Toast.makeText(MainActivity.this, "Bluetooth On", Toast.LENGTH_LONG).show();
                        isEnabled = true;
                    }
                }
            });

            bt_off.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btAdapter.disable();
                    isEnabled = false;
                    available_devices.setVisibility(View.INVISIBLE);
                }
            });

            bt_drive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isConnected) {
                        Intent intent = new Intent(MainActivity.this, Car_Controller.class);
                        ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(MainActivity.this, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        startActivity(intent, activityOptions.toBundle());
                    } else {
                        Toast.makeText(MainActivity.this, "Connect To Car First", Toast.LENGTH_LONG).show();
                    }
                }
            });

            mov.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    try {
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            outputStream.write('0');
                            return true;
                        }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                            outputStream.write('5');
                            return true;
                        }
                    }catch (IOException ex){
                        ex.printStackTrace();
                    }
                    return false;
                }
            });


        } else {
            isEnabled = false;
            Toast.makeText(this, "Your Device Doesn't Support Bluetooth Adapter", Toast.LENGTH_LONG).show();
        }

        available_devices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DeviceName = devices.get(position);
                //System.out.println("DEVICES NAME : "+DeviceName);
                connectBT();
            }
        });
    }

    private void startDiscovery() {
        Set<BluetoothDevice> deviceSet =
                btAdapter.getBondedDevices();
        System.out.println("DEVICE SET "+deviceSet.toString());


        if(!deviceSet.isEmpty()){
            for(BluetoothDevice iterator : deviceSet){
                devices.add(iterator.getName());
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,devices);
            available_devices.setAdapter(arrayAdapter);
        }
    }

    public void connectBT(){
        Set<BluetoothDevice> deviceSet = btAdapter.getBondedDevices();

        if(DeviceName != null){
            for(BluetoothDevice iterator : deviceSet){
                if(iterator.getName().equals(DeviceName)){
                    btDevice = iterator;
                    break;
                }
            }
        }

        if(btDevice != null){
            try {
                socket = btDevice.createRfcommSocketToServiceRecord(btDevice.getUuids()[0].getUuid());

                socket.connect();

                if(socket.isConnected()){
                    System.out.println(btDevice.getName());
                    status.setText("Connected To : " + btDevice.getName());
                    inputStream = socket.getInputStream();
                    outputStream = socket.getOutputStream();
                    isConnected = true;
                }else{
                    Toast.makeText(MainActivity.this,"Unable to Connect !!",Toast.LENGTH_LONG).show();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static BluetoothSocket reSock(){
        if(isConnected){
            System.out.println("Connected");

        }
        return socket;
    }
}