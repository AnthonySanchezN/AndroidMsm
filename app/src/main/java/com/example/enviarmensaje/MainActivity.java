package com.example.enviarmensaje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etTelefono, etMensaje;
    Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTelefono = findViewById(R.id.et_telefono);
        etMensaje = findViewById(R.id.et_mensaje);
        btnEnviar = findViewById(R.id.btn_enviar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED){
                    enviarMensaje();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 100);

                }
            }
        });
    }

    private void enviarMensaje() {
        String eTelefono = etTelefono.getText().toString().trim();
        String eMensaje = etMensaje.getText().toString().trim();

        if(!eTelefono.equals("") && !eMensaje.equals("")){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(eTelefono, null, eMensaje, null, null);
            Toast.makeText(getApplicationContext(), "Mensaje enviado con exito", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Ingrese mensaje", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            enviarMensaje();
        } else {
            Toast.makeText(getApplicationContext(), "Acceso denegado", Toast.LENGTH_SHORT).show();
        }
    }
}