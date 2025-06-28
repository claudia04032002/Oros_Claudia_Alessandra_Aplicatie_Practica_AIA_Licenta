package com.example.controlvoce;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    MainActivity context = this;
    BluetoothSocket btSocket = null;

    public static String[] GetStringArray(ArrayList<String> arr)
    {

        // declaration and initialise String Array
        String str[] = new String[arr.size()];

        // ArrayList to Array Conversion
        for (int j = 0; j < arr.size(); j++) {

            // Assign each value to String array
            str[j] = arr.get(j);
        }

        return str;
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)
                    ||!(ContextCompat.checkSelfPermission(this, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED)
                    ||!(ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED)
                    ||!(ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED)
                    ||!(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    ||!(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            ) {
                int GRANTED = 1;
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.VIBRATE,
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, GRANTED);
                return false;
            }else{
                return true;
            }
        }
        return true;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView list=(ListView)findViewById(R.id.list);
        final Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        final EditText editText = findViewById(R.id.editText);
        final SpeechRecognizer mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        final ImageButton button = (ImageButton)findViewById(R.id.button);

        button.setVisibility(View.GONE);
        editText.setVisibility(View.GONE);

        while(!checkPermission());

        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        }

        final ArrayList <String> name = new ArrayList();
        final ArrayList <String> address = new ArrayList();
        final ArrayList <String> addr = new ArrayList();

        String[] str1 = new String[] {"No devices"};
        String[] str2 = new String[] {""};

        myAdapter adapter = new myAdapter(context, str1, str2);
        list.setAdapter(adapter);

        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
                        Log.d("aaa", Integer.toString(pairedDevices.size()));
                        if (pairedDevices.size() > 0) {
                            for (BluetoothDevice device : pairedDevices) {
                                name.add("Nume:" + device.getName());
                                address.add("MAC:" + device.getAddress());
                                addr.add(device.getAddress());
                            }
                            myAdapter adapter = new myAdapter(context, name.toArray(new String[name.size()]), address.toArray(new String[address.size()]));
                            list.setAdapter(adapter);
                            t.cancel();
                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    try{
                                        if (btSocket == null) {
                                            Log.d("aaa", "connecting...");
                                            BluetoothDevice device = bluetoothAdapter.getRemoteDevice(GetStringArray(addr)[position]);
                                            btSocket = device.createInsecureRfcommSocketToServiceRecord(myUUID);
                                            bluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                                            btSocket.connect();
                                            Log.d("aaa", "connected");
                                            list.setVisibility(View.GONE);
                                            button.setVisibility(View.VISIBLE);
                                            editText.setVisibility(View.VISIBLE);
                                        }
                                    }catch (IOException e) {
                                        Log.d("aaa", "Failed");
                                        Toast.makeText(context,"Could not connect!",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }, 1000, 1000);


        final Intent mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());

        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override public void onReadyForSpeech(Bundle bundle) {}
            @Override public void onBeginningOfSpeech() {}
            @Override public void onRmsChanged(float v) {}
            @Override public void onBufferReceived(byte[] bytes) {}
            @Override public void onEndOfSpeech() {}
            @Override public void onError(int i) {}
            @Override public void onPartialResults(Bundle bundle) {}
            @Override public void onEvent(int i, Bundle bundle) {}
            @Override public void onResults(Bundle bundle) {
                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                Log.d("aaa", "done recognising");
                if (matches != null) {
                    editText.setText(matches.get(0));
                    Log.d("aaa", Boolean.toString(matches.get(0).equals("front")));
                    int result = -1;
                    for(int i = 0; i < matches.size(); i++) {
                        if(matches.get(i).equals("stop") || matches.get(i).equals("oprește") || matches.get(i).equals("oprește te")){
                            result = 0;
                            break;
                        }else if(matches.get(i).equals("front") || matches.get(i).equals("forward") || matches.get(i).equals("față")) {
                            result = 1;
                            break;
                        }else if(matches.get(i).equals("back") || matches.get(i).equals("backward") || matches.get(i).equals("spate") || matches.get(i).equals("bec")){
                            result = 2;
                            break;
                        }else if(matches.get(i).equals("left") || matches.get(i).equals("stânga")){
                            result = 3;
                            break;
                        }else if(matches.get(i).equals("right") || matches.get(i).equals("dreapta")){
                            result = 4;
                            break;
                        }
                    }
                    Log.d("aaa", Integer.toString(result));
                    if(result != -1){
                        try {
                            btSocket.getOutputStream().write(Integer.toString(result).getBytes());
                            Log.d("aaa", "success");
                        } catch (IOException e){
                            Log.d("aaa", "failed");
                        }
                    }
                }
            }
        });

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            v.vibrate(VibrationEffect.createOneShot(75, VibrationEffect.EFFECT_TICK));
                        } else {
                            v.vibrate(75);
                        }
                        mSpeechRecognizer.stopListening();
                        editText.setHint("You will see input here");
                        break;
                    case MotionEvent.ACTION_DOWN:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            v.vibrate(VibrationEffect.createOneShot(75, VibrationEffect.EFFECT_TICK));
                        } else {
                            v.vibrate(75);
                        }
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        editText.setText("");
                        editText.setHint("Listening...");
                        break;
                }
                return false;
            }
        });
    }



}
