package com.edgar_avc.supportingplanet.Ventanas.Administrador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.edgar_avc.supportingplanet.Model.Usuario;
import com.edgar_avc.supportingplanet.Model.Valores;
import com.edgar_avc.supportingplanet.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PesoActivity extends AppCompatActivity {

    private ImageView iv_peso;
    private TextView tv_peso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peso);

        Intent intent = getIntent();
        int clave = intent.getIntExtra("clave",1);

        iv_peso = findViewById(R.id.iv_peso);
        tv_peso = findViewById(R.id.tv_peso);


        if(clave==1)
        {
            iv_peso.setImageResource(R.drawable.peso1);

            final ProgressDialog dialog = ProgressDialog.show(PesoActivity.this, "Calculando peso", "Colóquese sobre la bascula", true);

            DatabaseReference bbdd = FirebaseDatabase.getInstance().getReference("VALORES");
            bbdd.child("PESANDO").setValue("TRUE");

            DatabaseReference bdPeso = FirebaseDatabase.getInstance().getReference("VALORES");
            bdPeso.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Valores valores = dataSnapshot.getValue(Valores.class);
                    if(valores.getPESANDO().equals("FALSE"))
                    {
                        tv_peso.setText("Peso: " + valores.getPESO() + "kg");
                        dialog.dismiss();
                        temporizador();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else
        {
            iv_peso.setImageResource(R.drawable.estatura1);
            tv_peso.setText("Estatura: 1.70m");
            temporizador();
        }

    }

    private void temporizador()
    {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(PesoActivity.this, EsperaActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4500);
    }

}
