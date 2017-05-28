package com.hmproductions.backgroundmodifier;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView redTV, greenTV, blueTV;
    Button redButton, greenButton, blueButton;
    int redValue, greenValue, blueValue;

    private static final String COLOR_TAG = "COLOR STATE";

    private static final String RED_KEY = "red-color";
    private static final String GREEN_KEY = "green-color";
    private static final String BLUE_KEY = "blue-color";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        redButton = (Button)findViewById(R.id.redButton);
        greenButton = (Button)findViewById(R.id.greenButton);
        blueButton = (Button)findViewById(R.id.blueButton);

        redTV = (TextView)findViewById(R.id.redTextView);
        greenTV = (TextView)findViewById(R.id.greenTextView);
        blueTV = (TextView)findViewById(R.id.blueTextView);

        setupColorStates();
        changeBackgroundColor();

        RedButtonClickListener();
        GreenButtonClickListener();
        BlueButtonClickListener();
    }

    private void setupColorStates()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        redValue = preferences.getInt(RED_KEY,0);
        greenValue = preferences.getInt(GREEN_KEY,0);
        blueValue = preferences.getInt(BLUE_KEY,0);

        redTV.setText(String.valueOf(redValue));
        greenTV.setText(String.valueOf(greenValue));
        blueTV.setText(String.valueOf(blueValue));
    }

    private void saveColorStates()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(RED_KEY,redValue);
        editor.putInt(GREEN_KEY,greenValue);
        editor.putInt(BLUE_KEY,blueValue);

        editor.apply();
    }


    // Button Listeners

    private void RedButtonClickListener()
    {
        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.parseInt(redTV.getText().toString());
                value+=10;

                if(value>100)
                    value=0;

                redValue = value;
                redTV.setText(String.valueOf(value));

                changeBackgroundColor();
            }
        });
    }

    private void GreenButtonClickListener()
    {
        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.parseInt(greenTV.getText().toString());
                value+=10;

                if(value>100)
                    value=0;

                greenValue = value;
                greenTV.setText(String.valueOf(value));

                changeBackgroundColor();
            }
        });
    }

    private void BlueButtonClickListener()
    {
        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.parseInt(blueTV.getText().toString());
                value+=10;

                if(value>100)
                    value=0;

                blueValue = value;
                blueTV.setText(String.valueOf(value));

                changeBackgroundColor();
            }
        });
    }



    /*
        changeBackgroundColor()
        Converts Red, Green , Blue Integer values which range from [0,100] to hexadecimal
        and then changes the background color.
    */
    private void changeBackgroundColor()
    {
        int hexIntRed = redValue * 255 / 100;                // Example 50 to 127  OR 100 to 255
        int hexIntGreen = greenValue  * 255 / 100;
        int hexIntBlue = blueValue  * 255 / 100;

        String hexRed = Integer.toHexString(hexIntRed);
        String hexGreen = Integer.toHexString(hexIntGreen);
        String hexBlue = Integer.toHexString(hexIntBlue);

        hexRed = checkIfSingle(hexRed);
        hexGreen = checkIfSingle(hexGreen);
        hexBlue = checkIfSingle(hexBlue);

        String color = "#" + hexRed + hexGreen + hexBlue;

        Log.v(COLOR_TAG, color);

        View baseView = findViewById(R.id.baseView);
        baseView.setBackgroundColor(Color.parseColor(color));
    }


    /*
        checkIfSingle()
        Checks if the hexadecimal) value is single digit, it adds one 0 in front
     */
    private String checkIfSingle(String hexColor)
    {
        if(hexColor.length() == 1)
            return "0" + hexColor;
        else
            return hexColor;
    }


    // Saving Color States
    @Override
    protected void onPause() {
        super.onPause();
        saveColorStates();
    }
}
