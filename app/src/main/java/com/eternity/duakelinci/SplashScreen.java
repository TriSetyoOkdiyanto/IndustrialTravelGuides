package com.eternity.duakelinci;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.eternity.duakelinci.ui.MainActivity;

/**
 * Created by TRI SETYO on 05/02/2016.
 */
public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent openMainActivity= new Intent(SplashScreen.this , MainActivity.class);
                    startActivity(openMainActivity);
                }
            }
        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}