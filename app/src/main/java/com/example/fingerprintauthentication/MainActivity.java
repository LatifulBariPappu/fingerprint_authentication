package com.example.fingerprintauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView msg_text = findViewById(R.id.textMsg);
        Button login_Btn = findViewById(R.id.loginBtn);

        BiometricManager biometricManager = BiometricManager.from(this);
        if (biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS) {
            msg_text.setText("You can use the fingerprint sensor to login");
            msg_text.setTextColor(Color.rgb(255, 255, 255));
        } else if (biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE) {
            msg_text.setText("the device don't have a fingerprint sensor");
            login_Btn.setVisibility(View.GONE);
        } else if (biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE) {
            msg_text.setText("the biometric sensors is currently unavailable");
            login_Btn.setVisibility(View.GONE);
        } else if (biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED) {
            msg_text.setText("your device don't have any fingerprint saved, Please check your security settings");
            login_Btn.setVisibility(View.GONE);
        }

        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login")
                .setDescription("User your fingerprint to login to your app")
                .setNegativeButtonText("Cancel")
                .build();

        login_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);
            }
        });
    }
}