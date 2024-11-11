package com.cloudpos.pwd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent serviceIntent = new Intent(this, PasswordService.class);
        startService(serviceIntent);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
