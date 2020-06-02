package com.edgar_avc.supportingplanet.Ventanas.Usuario.Fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.edgar_avc.supportingplanet.Ventanas.LoginActivity;
import com.edgar_avc.supportingplanet.R;
import com.edgar_avc.supportingplanet.Model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class PerfilFragment extends Fragment {

    final TextView infoUsuario[] = new TextView[4];


    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        showToolbar("Perfil",false,view);

        //OBTENER INFORMACION DEL USUARIO
        ObtenerInfo(view);


        //BOTON CERRAR SESION
        Button button = view.findViewById(R.id.ButtonEndSession);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EndSession();
            }
        });


        return view;
    }


    public  void ObtenerInfo(View view)
    {
        //Asignar controles
        infoUsuario[0] = view.findViewById(R.id.tv_perfil_nombre1);
        infoUsuario[1] = view.findViewById(R.id.tv_perfil_nombre2);
        infoUsuario[2] = view.findViewById(R.id.tv_perfil_apellidos);
        infoUsuario[3] = view.findViewById(R.id.tv_perfil_correo);




        //OBTENER USUARIO ACTUAL
        String vUsuario,vContraseña;
        SharedPreferences sharedPref = getActivity().getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);
        vUsuario = sharedPref.getString("spUser","null");
        vContraseña = sharedPref.getString("spPassword","null");


        //CONSULTAR A LA BD LA INFORMACIÓN DEL USUARIO LOGUEADO
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("USUARIOS");
        Query q= mDatabase.orderByChild("correo").equalTo(vUsuario);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                    try{
                        Usuario usuario = dataSnapshot1.getValue(Usuario.class);
                        infoUsuario[0].setText(usuario.getNOMBRE() );
                        infoUsuario[1].setText(usuario.getNOMBRE() );
                        infoUsuario[2].setText(usuario.getAP()+" "+usuario.getAM());
                        infoUsuario[3].setText(usuario.getCORREO());
                    }
                    catch (Exception e)
                    {
                        Log.d("SALIDA",e.toString());
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
    }


    public void EndSession()
    {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
        dialogo1.setTitle("Importante");
        dialogo1.setMessage("¿Desea cerrar sesion?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                //REESTABLECER EL USUARIO
                SharedPreferences sharedPref = getActivity().getSharedPreferences("LoginPreferences",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("spUser","null");
                editor.putString("spPassword","null");
                editor.apply();

                //REGRESAR A PANTALLA INICIO SESION
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        });
        dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                //cancelar();
            }
        });
        dialogo1.show();
    }



    public void showToolbar(String title, boolean upButton, View view )
    {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }
}
