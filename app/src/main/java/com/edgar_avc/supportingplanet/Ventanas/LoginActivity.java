package com.edgar_avc.supportingplanet.Ventanas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import com.edgar_avc.supportingplanet.Ventanas.Administrador.EsperaActivity;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.edgar_avc.supportingplanet.R;
import com.edgar_avc.supportingplanet.Model.Usuario;
import com.edgar_avc.supportingplanet.Ventanas.Administrador.MenuActivity;
import com.edgar_avc.supportingplanet.Ventanas.Usuario.ContainerActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    DatabaseReference bbdd = FirebaseDatabase.getInstance().getReference("USUARIOS");
    TextInputEditText correo, contra;
    TextView crearCuenta;

    Button sesion;
    String usuario, contrasena;

    String tipoUsuario;


    @Override
    public void onBackPressed() {

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage("¿Desea salir de la app?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                /*finish();
                System.exit(0);*/
            }
        });
        dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                //cancelar();
            }
        });
        dialogo1.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //VERIFICAR USUARIO
        String vUsuario,vContraseña;

        SharedPreferences sharedPref = getSharedPreferences("LoginPreferences",Context.MODE_PRIVATE);
        vUsuario = sharedPref.getString("spUser","null");
        vContraseña = sharedPref.getString("spPassword","null");
        tipoUsuario = sharedPref.getString("spTipo","null");

        /*if(!vUsuario.equals("null"))
        {
            Intent intent = new Intent(LoginActivity.this, ContainerActivity.class);
            startActivity(intent);
            IniciarSesion(vUsuario,vContraseña);
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        correo = (TextInputEditText) findViewById(R.id.TIusername);
        contra = (TextInputEditText) findViewById(R.id.TIpassword);
        sesion = (Button) findViewById(R.id.Blogin);

        crearCuenta = findViewById(R.id.tv_CrearCuenta);


        crearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });


        sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
               Query q= bbdd.orderByChild("correo").equalTo(correo.getText().toString());
               q.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {

                       for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                           Usuario users = dataSnapshot1.getValue(Usuario.class);

                            contrasena= users.getCONTRASEÑA();
                            usuario= users.getCORREO();
                            tipoUsuario = users.getTIPO();
                       }

                       if (correo.getText().toString().equals(usuario) && contra.getText().toString().equals(contrasena)){

                           IniciarSesion(usuario,contrasena);

                       }
                       else
                           Toast.makeText(LoginActivity.this,"Usuario o contraseña incorrectos",Toast.LENGTH_SHORT).show();

                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });
            }
        });
    }

    public void IniciarSesion(String user, String pass)
    {
        SaveLogin(user,pass);

        if(tipoUsuario.equals("Administrador"))
        {
            Intent intent = new Intent(LoginActivity.this, EsperaActivity.class);
            startActivity(intent);
            Log.d("salida","inicio administrador");
        }
        else
        {
            Intent intent = new Intent(LoginActivity.this, ContainerActivity.class);
            startActivity(intent);
            finish();
            Log.d("salida","inicio NORMAL");
        }
    }

    public void SaveLogin(String user, String password )
    {
        SharedPreferences sharedPref = getSharedPreferences("LoginPreferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("spUser",user);
        editor.putString("spPassword",password);
        editor.putString("spTipo",tipoUsuario);
        editor.apply();
    }

}

