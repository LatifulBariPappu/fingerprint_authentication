package com.example.fingerprintauthentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView msg_text=findViewById(R.id.textMsg);
        Button login_Btn=findViewById(R.id.loginBtn);

        BiometricManager biometricManager= BiometricManager.from(this);
        if(biometricManager.canAuthenticate()==BiometricManager.BIOMETRIC_SUCCESS){
            msg_text.setText("You can use the fingerprint sensor to login");
            msg_text.setTextColor(Color.rgb(255,255,255));
        } else if (biometricManager.canAuthenticate()==BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE) {
            msg_text.setText("the device don't have a fingerprint sensor");
            login_Btn.setVisibility(View.GONE);
        }else if (biometricManager.canAuthenticate()==BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE) {
            msg_text.setText("the biometric sensors is currently unavailable");
            login_Btn.setVisibility(View.GONE);
        }else if (biometricManager.canAuthenticate()==BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED) {
            msg_text.setText("your device don't have any fingerprint saved, Please check your security settings");
            login_Btn.setVisibility(View.GONE);

        }
    }
}