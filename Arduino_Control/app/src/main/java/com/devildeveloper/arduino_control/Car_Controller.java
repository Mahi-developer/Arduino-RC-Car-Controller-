package com.devildeveloper.arduino_control;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Car_Controller extends AppCompatActivity {

    private com.airbnb.lottie.LottieAnimationView lottieAnimationView;
    private OutputStream outputStream;
    private InputStream inputStream;
    private BluetoothSocket socket;
    private ImageButton bt_left,bt_right,bt_forward,bt_back;
    private Button _1,_2,_3,_4,_5;
    private Switch f_l,b_l;
    private ImageView iv;
    int gear;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car__controller);

        //hooks
        bt_left = findViewById(R.id.left);
        bt_right = findViewById(R.id.right);
        bt_forward = findViewById(R.id.forward);
        bt_back = findViewById(R.id.backward);
        _1 = findViewById(R.id.gear_1);
        _2 = findViewById(R.id.gear_2);
        _3 = findViewById(R.id.gear_3);
        _4 = findViewById(R.id.gear_4);
        _5 = findViewById(R.id.gear_5);
        f_l = findViewById(R.id.f_led);
        b_l = findViewById(R.id.b_led);
        iv = findViewById(R.id.i_v);
        lottieAnimationView = findViewById(R.id.lottie);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        Animation zoom_in = AnimationUtils.loadAnimation(this,R.anim.zoom_out);
        iv.startAnimation(zoom_in);

        _1.setBackgroundResource(R.drawable.ledshapeon);

        //getStreams
        socket = MainActivity.reSock();

        if(socket != null){
            System.out.println("Socket Available");
            try {
                outputStream = socket.getOutputStream();//getting Bluetooth Socket
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(this,"Socket is not available!!",Toast.LENGTH_LONG).show();
        }

        //Gear_1
        _1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    outputStream.write('a');//1st Gear
                    _1.setBackgroundResource(R.drawable.ledshapeon);
                    _2.setBackgroundResource(R.drawable.shape);
                    _3.setBackgroundResource(R.drawable.shape);
                    _4.setBackgroundResource(R.drawable.shape);
                    _5.setBackgroundResource(R.drawable.shape);
                    gear = 0;
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Car_Controller.this,e.toString(),Toast.LENGTH_LONG).show();

                }
            }
        });

        //Gear_2
        _2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    outputStream.write('b');//2nd Gear
                    _2.setBackgroundResource(R.drawable.ledshapeon);
                    _1.setBackgroundResource(R.drawable.shape);
                    _3.setBackgroundResource(R.drawable.shape);
                    _4.setBackgroundResource(R.drawable.shape);
                    _5.setBackgroundResource(R.drawable.shape);
                    gear = 1;
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Car_Controller.this,e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });

        //Gear_3
        _3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    outputStream.write('c');//3rd Gear
                    _3.setBackgroundResource(R.drawable.ledshapeon);
                    _1.setBackgroundResource(R.drawable.shape);
                    _2.setBackgroundResource(R.drawable.shape);
                    _4.setBackgroundResource(R.drawable.shape);
                    _5.setBackgroundResource(R.drawable.shape);
                    gear = 2;
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Car_Controller.this,e.toString(),Toast.LENGTH_LONG).show();

                }
            }
        });

        //Gear_4
        _4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    outputStream.write('d');//4th Gear
                    _4.setBackgroundResource(R.drawable.ledshapeon);
                    _1.setBackgroundResource(R.drawable.shape);
                    _2.setBackgroundResource(R.drawable.shape);
                    _3.setBackgroundResource(R.drawable.shape);
                    _5.setBackgroundResource(R.drawable.shape);
                    gear =3;
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Car_Controller.this,e.toString(),Toast.LENGTH_LONG).show();

                }
            }
        });

        //Gear_5
        _5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    outputStream.write('e');//5th Gear
                    _5.setBackgroundResource(R.drawable.ledshapeon);
                    _1.setBackgroundResource(R.drawable.shape);
                    _2.setBackgroundResource(R.drawable.shape);
                    _3.setBackgroundResource(R.drawable.shape);
                    _4.setBackgroundResource(R.drawable.shape);
                    gear = 4;
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Car_Controller.this,e.toString(),Toast.LENGTH_LONG).show();

                }
            }
        });

        //Front_Led
        f_l.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    try {
                        outputStream.write('+');//switch on light
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(Car_Controller.this,e.toString(),Toast.LENGTH_LONG).show();

                    }
                }else{
                    try {
                        outputStream.write('-');//switch off light
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(Car_Controller.this,e.toString(),Toast.LENGTH_LONG).show();

                    }
                }
            }
        });

        //back_led
        b_l.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    try {
                        outputStream.write('=');//switch on light
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(Car_Controller.this,e.toString(),Toast.LENGTH_LONG).show();

                    }
                }else{
                    try {
                        outputStream.write('_');//switch off light
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(Car_Controller.this,e.toString(),Toast.LENGTH_LONG).show();

                    }
                }
            }
        });

        //turn left
        bt_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try{
                    if(event.getAction() == MotionEvent.ACTION_DOWN) {
                        bt_left.setBackgroundResource(R.drawable.greenleft);
                        bt_left.getLayoutParams().height = 180;
                        bt_left.getLayoutParams().width = 210;
                        bt_left.requestLayout();
                        outputStream.write('2');
                        return true;
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        bt_left.setBackgroundResource(R.drawable.greyleft);
                        bt_left.getLayoutParams().height = 195;
                        bt_left.getLayoutParams().width = 225;
                        bt_left.requestLayout();
                        outputStream.write('5');
                        return true;
                    }
                }catch (IOException e){
                    e.printStackTrace();
                    Toast.makeText(Car_Controller.this,e.toString(),Toast.LENGTH_LONG).show();

                }
                return false;
            }
        });

        //turn right
        bt_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try{
                    if(event.getAction() == MotionEvent.ACTION_DOWN) {
                        outputStream.write('3');
                        bt_right.setBackgroundResource(R.drawable.greenleft);
                        bt_right.getLayoutParams().height = 180;
                        bt_right.getLayoutParams().width = 210;
                        bt_right.requestLayout();
                        return true;
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        outputStream.write('5');
                        bt_right.setBackgroundResource(R.drawable.greyleft);
                        bt_right.getLayoutParams().height = 195;
                        bt_right.getLayoutParams().width = 225;
                        bt_right.requestLayout();
                        return true;
                    }
                }catch (IOException e){
                    e.printStackTrace();
                    Toast.makeText(Car_Controller.this,e.toString(),Toast.LENGTH_LONG).show();

                }
                return false;
            }
        });

        //move forward
        bt_forward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try{
                    if(event.getAction() == MotionEvent.ACTION_DOWN) {
                        outputStream.write('0');
                        lottieAnimationView.setSpeed(1);
                        switch (gear){
                            case 1:
                                lottieAnimationView.setMaxFrame(55);
                                break;
                            case 2:
                                lottieAnimationView.setMaxFrame(80);
                                break;
                            case 3:
                                lottieAnimationView.setMaxFrame(100);
                                break;
                            case 4:
                                lottieAnimationView.setMaxFrame(120);
                                break;
                            default:
                                lottieAnimationView.setMaxFrame(24);
                                break;
                        }
                        lottieAnimationView.playAnimation();
                        bt_forward.getLayoutParams().width = 125;
                        bt_forward.getLayoutParams().height = 330;
                        bt_forward.requestLayout();
                        return true;
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        outputStream.write('5');
                        lottieAnimationView.reverseAnimationSpeed();
                        switch (gear){
                            case 1:
                                lottieAnimationView.setMaxFrame(55);
                                break;
                            case 2:
                                lottieAnimationView.setMaxFrame(80);
                                break;
                            case 3:
                                lottieAnimationView.setMaxFrame(100);
                                break;
                            case 4:
                                lottieAnimationView.setMaxFrame(120);
                                break;
                            default:
                                lottieAnimationView.setMaxFrame(24);
                                break;
                        }
                        lottieAnimationView.playAnimation();
                        bt_forward.getLayoutParams().width = 135;
                        bt_forward.getLayoutParams().height = 360;
                        bt_forward.requestLayout();
                        return true;
                    }
                }catch (IOException e){
                    Toast.makeText(Car_Controller.this,e.toString(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                return false;
            }
        });

        //move backward
        bt_back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try{
                    if(event.getAction() == MotionEvent.ACTION_DOWN) {
                        outputStream.write('1');
                        bt_back.getLayoutParams().height = 325;
                        bt_back.getLayoutParams().width = 125;
                        bt_back.requestLayout();
                        return true;
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        outputStream.write('5');
                        bt_back.getLayoutParams().height = 340;
                        bt_back.getLayoutParams().width = 140;
                        bt_back.requestLayout();
                        return true;
                    }
                }catch (IOException e){
                    e.printStackTrace();
                    Toast.makeText(Car_Controller.this,e.toString(),Toast.LENGTH_LONG).show();

                }
                return false;
            }
        });
    }
}