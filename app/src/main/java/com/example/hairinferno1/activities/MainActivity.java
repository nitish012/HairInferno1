package com.example.hairinferno1.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hairinferno1.R;
import com.example.hairinferno1.R;

public class MainActivity extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_LENGTH = 2000;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkCode();
    }

    private void splashScreen() {

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(MainActivity.this,Login.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void checkCode()
    {
        int CODE;
        sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
        CODE=sharedPreferences.getInt("code",2);
        if(CODE==200)
        {
            Intent intent=new Intent(this,Home.class);
            startActivity(intent);
            finish();
        }

        else
        {
            splashScreen();
        }

    }
}
