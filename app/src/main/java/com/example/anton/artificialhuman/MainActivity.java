package com.example.anton.artificialhuman;

import android.app.admin.DevicePolicyManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    Button rec_voice_btn, stop_serv_btn, start_serv_btn, print_serv_btn, sendUDPmsg_btn;
    TextView recogn_text_view;
    TextToSpeech t1;
    DevicePolicyManager mDPM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//DEBUG NOT SLEEP!
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        recogn_text_view = (TextView) findViewById(R.id.text_recognized_view);
        rec_voice_btn = (Button) findViewById(R.id.rec_voice_button);
        start_serv_btn = (Button) findViewById(R.id.start_serv);
        stop_serv_btn = (Button) findViewById(R.id.stop_serv);
        print_serv_btn = (Button) findViewById(R.id.print_serv);
        sendUDPmsg_btn = (Button) findViewById(R.id.send_udp_button);

        rec_voice_btn.setOnClickListener(this);
        start_serv_btn.setOnClickListener(this);
        stop_serv_btn.setOnClickListener(this);
        print_serv_btn.setOnClickListener(this);
        sendUDPmsg_btn.setOnClickListener(this);

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rec_voice_button:
                Toast.makeText(getApplicationContext(), "Check Button", Toast.LENGTH_LONG).show();
                handleRecordedSpeech();
                break;
            case R.id.start_serv:
                startService(new Intent(MainActivity.this, TestService.class));
                break;
            case R.id.stop_serv:
                stopService(new Intent(MainActivity.this, TestService.class));
                break;
            case R.id.print_serv:
                Ut.printRunningServices(this);
                break;
            case R.id.send_udp_button:
                //send_UDP_msg();
                new_send_UDP();
//                runUdpClient();
                break;
        }
    }

    public void new_send_UDP()
    {
        String dataText = "Hello";
        String port = "48655";
        String host = "192.168.0.102";
        String uriString = "udp://" + host + ":" + port + "/";
        uriString += Uri.encode(dataText);
        Log.e(Ut.LOG_TAG,"new_send_UDP = " + uriString);
        Toast.makeText(getApplicationContext(), "Send UDP message new_send_UDP() ", Toast.LENGTH_LONG).show();
        Uri uri = Uri.parse(uriString);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivity(intent);
    }
    public void send_UDP_msg() {
//        byte[] message = new byte[1500];
//        DatagramPacket p = new DatagramPacket(message, message.length);
//        DatagramSocket s = new DatagramSocket(server_port);
//        InetAddress IPAddress =  InetAddress.getByName("192.168.0.102");
        //DatagramPacket send_packet = new DatagramPacket(send_data,str.length(), IPAddress, 2362);
//        client_socket.send(send_packet);
        Toast.makeText(getApplicationContext(), "Send UDP message", Toast.LENGTH_LONG).show();
        DatagramSocket ds = null;
        String Message = "Android Message";
        try {
            InetAddress IPAddress = InetAddress.getByName("255.255.255.255");
            ds = new DatagramSocket(48655);
            DatagramPacket dp;
//        dp = new DatagramPacket(Message.getBytes(), Message.length(),"255.255.255.255,48655);
//            dp = new DatagramPacket(Message.getBytes(), Message.length());
            dp = new DatagramPacket(Message.getBytes(), Message.length(), IPAddress, 48655);
//            ds.setBroadcast(true);
            ds.send(dp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void runUdpClient() {
        final int UDP_SERVER_PORT = 48655;
        String udpMsg = "hello world from UDP client" + UDP_SERVER_PORT;
        Toast.makeText(getApplicationContext(), "Send UDP message runUdpClient", Toast.LENGTH_LONG).show();
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket();
            InetAddress serverAddr = InetAddress.getByName("192.168.0.102");

            DatagramPacket dp;
            Log.e(Ut.LOG_TAG,"runUdpClient = " + udpMsg.getBytes() + " " + udpMsg.length() + " "
                                + serverAddr + UDP_SERVER_PORT + " " + serverAddr.toString());
//            dp = new DatagramPacket(udpMsg.getBytes(), udpMsg.length(), serverAddr, UDP_SERVER_PORT);
            dp = new DatagramPacket(udpMsg.getBytes(), udpMsg.length(), serverAddr, UDP_SERVER_PORT);
//            DatagramPacket packet = new DatagramPacket(data, data.length,
//                    InetAddress.getByName(ip.getText().toString()),
//                    Integer.valueOf(port.getText().toString()));
            ds.send(dp);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ds != null) {
                ds.close();
            }
        }
    }


    public void handleRecordedSpeech (){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE,"ee");//Doesn't work !!!
        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say something");

        try {
            startActivityForResult(i,100);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),"Sorry your device doesn't support speech language",Toast.LENGTH_LONG).show();
        }
    }

    public void onActivityResult(int req_code, int res_code, Intent i) {
        super.onActivityResult(req_code,res_code,i);
        Log.e(Ut.LOG_TAG,"onActivityResult req_code = " + req_code );
        if (res_code == RESULT_OK && i != null) {
            switch (req_code) {
                case 100:
                {
                    ArrayList<String> result = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                    Toast.makeText(getApplicationContext(), "" + result.get(0), Toast.LENGTH_LONG).show();
                    recogn_text_view.setText(result.get(0));
                    String str = "open google";
                    if( str.equalsIgnoreCase(result.get(0)) ) {
                        String url = "http://www.google.com";
                        Intent c = new Intent(Intent.ACTION_VIEW);
                        c.setData(Uri.parse(url));
                        startActivity(c);
                    }
                    t1.speak(result.get(0), TextToSpeech.QUEUE_FLUSH, null);
//                    if(result.get(0).equalsIgnoreCase("lock")) {
//                        mDPM = (DevicePolicyManager) getSystemService(getApplicationContext().DEVICE_POLICY_SERVICE);
//                        mDPM.lockNow();
//                    }
                }
                    break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
//            Toast.makeText(getApplicationContext(), "Test Menu", Toast.LENGTH_LONG).show();
//            Log.e(LOG_TAG,"Test Menu");
//            Ut.printRunningServices(getApplicationContext());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
