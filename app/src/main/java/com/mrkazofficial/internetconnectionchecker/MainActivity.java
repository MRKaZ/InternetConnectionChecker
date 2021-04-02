package com.mrkazofficial.internetconnectionchecker;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mrkazofficial.library.InternetChecker;

public class MainActivity extends AppCompatActivity {

    private ImageView setImage;
    private TextView textConnection;
    private TextView textConnectionType;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Change Support ActionBar Color
        Spannable text = new SpannableString(getTitle());
        text.setSpan(new ForegroundColorSpan(Color.WHITE), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        setTitle(text);

        setImage = findViewById(R.id.imageView);
        textConnection = findViewById(R.id.textConnection);
        textConnectionType = findViewById(R.id.textConnectionType);
        linearLayout = findViewById(R.id.LLayout);

        // Initiate the Connectivity Manager
        InternetChecker.initialize(this);
        // Register the Connectivity Manager
        InternetChecker.registerConnectivity(this);
        /*
         * Internet Connectivity listener
         * new InternetChecker().getResponse(InternetListener)
         */
        new InternetChecker().getResponse((connectionType, isConnection) -> {
            /*
             * "isConnection" Boolean value to check connection if true or false
             * "connectionType" String value to check type of connection
             */
            if (isConnection) {
                // If device connected to internet
                Toast.makeText(MainActivity.this, "Connected to internet!", Toast.LENGTH_SHORT).show();
                textConnection.setText(getString(R.string.connection_available));
                setImage.setBackgroundResource(R.drawable.failover);
                linearLayout.setVisibility(View.VISIBLE);
                Log.i("InternetChecker", "isConnection: true");

            } else {
                // If internet connection lost
                Toast.makeText(MainActivity.this, "No, Internet Connection!", Toast.LENGTH_SHORT).show();
                textConnection.setText(getString(R.string.connection_lost));
                setImage.setBackgroundResource(R.drawable.disconnect);
                linearLayout.setVisibility(View.VISIBLE);
                Log.e("InternetChecker", "isConnection: false");
            }
            // Get Connection Type which connection device does have
            Toast.makeText(MainActivity.this, connectionType, Toast.LENGTH_SHORT).show();
            textConnectionType.setText(connectionType);
            System.out.println(connectionType);
        });


    }


    @Override
    protected void onDestroy() {
        InternetChecker.unRegisterConnectivity(this);
        super.onDestroy();
    }
}