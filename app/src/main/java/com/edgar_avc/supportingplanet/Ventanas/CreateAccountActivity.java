package com.edgar_avc.supportingplanet.Ventanas;

import android.content.Intent;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.edgar_avc.supportingplanet.Class.CheckText;
import com.edgar_avc.supportingplanet.R;
import com.edgar_avc.supportingplanet.Model.Usuario;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountActivity extends AppCompatActivity {

    String CORREO;
    String TIPO;
    String CONTRASEÑA;
    String AM;
    String NOMBRE;
    String AP;

    TextInputEditText tiet[] = new TextInputEditText[6];
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        showToolbar("Crear usuario",true);

        button = findViewById(R.id.button_crear);


        tiet[0] = findViewById(R.id.tv_user_correo);
        tiet[1] = findViewById(R.id.tv_user_nombre);
        tiet[2] = findViewById(R.id.tv_user_ap);
        tiet[3] = findViewById(R.id.tv_user_am);
        tiet[4] = findViewById(R.id.tv_user_pass1);
        tiet[5] = findViewById(R.id.tv_user_pass2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckText checkText = new CheckText();
                if(checkText.FullFields(tiet))
                {
                    if( tiet[4].getText().toString().equals(tiet[5].getText().toString()) )
                    {
                        IngresarUsuario();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Las contraseñas no coinciden",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(getApplicationContext(),"Rellena todos los campos",Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void IngresarUsuario()
    {
        DatabaseReference bbdd =  FirebaseDatabase.getInstance().getReference("USUARIOS");

        try {
            String id = bbdd.push().getKey();

            CORREO = tiet[0].getText().toString();
            TIPO = "Normal";
            CONTRASEÑA = tiet[4].getText().toString();
            AM = tiet[3].getText().toString();
            NOMBRE = tiet[1].getText().toString();
            AP = tiet[2].getText().toString();

            Usuario usuario = new Usuario(CORREO, TIPO, CONTRASEÑA, AM, NOMBRE, AP);

            bbdd.child(id).setValue(usuario);

            Toast.makeText(getApplicationContext(),"Usuario registrado",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(CreateAccountActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();

        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Ocurrio un error",Toast.LENGTH_SHORT).show();
        }
    }

    public void showToolbar(String title, boolean upButton)
    {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }
}
