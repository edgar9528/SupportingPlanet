package com.edgar_avc.supportingplanet.Ventanas.Administrador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.edgar_avc.supportingplanet.Model.Frase;
import com.edgar_avc.supportingplanet.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class SuerteActivity extends AppCompatActivity {

    final ArrayList<Frase> frases = new ArrayList<>();
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suerte);

        textView = findViewById(R.id.tv_frase);

        DatabaseReference bbdd = FirebaseDatabase.getInstance().getReference("FRASES_SUERTE");
        Query q= bbdd.orderByKey();
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String clave, valor;
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    clave=  dataSnapshot1.getKey().toString();
                    valor = dataSnapshot1.getValue().toString();
                    Frase frase = new Frase( clave , valor  );
                    frases.add(frase);
                }
                mostrarFrase();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                temporizador();
            }
        });

    }

    private void mostrarFrase()
    {
        try
        {
            Random random = new Random();
            int numero = random.nextInt(frases.size());
            textView.setText( frases.get(numero).getValue() );
        }
        catch (Exception e)
        {
            textView.setText( frases.get(0).getValue() );
        }
        temporizador();
    }

    private void temporizador()
    {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SuerteActivity.this, EsperaActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4500);
    }
}
