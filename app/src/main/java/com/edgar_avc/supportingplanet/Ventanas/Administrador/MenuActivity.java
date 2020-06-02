package com.edgar_avc.supportingplanet.Ventanas.Administrador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.edgar_avc.supportingplanet.R;

public class MenuActivity extends AppCompatActivity {

    private ImageButton bt1,bt2,bt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        bt1= findViewById(R.id.bt1);
        bt2= findViewById(R.id.bt2);
        bt3 = findViewById(R.id.bt3);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, PesoActivity.class);
                intent.putExtra("clave",1);
                startActivity(intent);
                finish();
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, PesoActivity.class);
                intent.putExtra("clave",2);
                startActivity(intent);
                finish();
            }
        });



        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, SuerteActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
