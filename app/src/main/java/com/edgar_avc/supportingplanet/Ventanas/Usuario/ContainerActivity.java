package com.edgar_avc.supportingplanet.Ventanas.Usuario;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.edgar_avc.supportingplanet.R;
import com.edgar_avc.supportingplanet.Ventanas.LoginActivity;
import com.edgar_avc.supportingplanet.Ventanas.Usuario.Fragments.HomeFragment;
import com.edgar_avc.supportingplanet.Ventanas.Usuario.Fragments.HistorialFragment;
import com.edgar_avc.supportingplanet.Ventanas.Usuario.Fragments.PerfilFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class ContainerActivity extends AppCompatActivity {

    String vUsuario,vContraseña,vTipo;

    @Override
    public void onBackPressed() {

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage("¿Desea salir de la app?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

                Intent intent = new Intent(ContainerActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        BottomBar bottomBar = findViewById(R.id.bottombar);

        ObtenerUsuario();


        int tab=1;

        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            tab = extras.getInt("tab");
        }

        switch (tab)
        {
            case 1:
                bottomBar.setDefaultTab(R.id.TabHome);
                break;
            case 2:
                bottomBar.setDefaultTab(R.id.Tab2);
                break;
            case 3:
                bottomBar.setDefaultTab(R.id.Tab3);
                break;
            default:
                bottomBar.setDefaultTab(R.id.TabHome);
                break;
        }

        bottomBar.setOnTabSelectListener(new OnTabSelectListener()
        {
            @Override
            public void onTabSelected(int tabId)
            {
                switch (tabId)
                {
                    case R.id.TabHome:
                        HomeFragment homeFragment = new HomeFragment();
                                getSupportFragmentManager().beginTransaction().replace(R.id.containerPrincipal, homeFragment)
                                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                        .addToBackStack(null).commit();
                        break;

                    case R.id.Tab2:
                        HistorialFragment historialFragment = new HistorialFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerPrincipal, historialFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;

                    case R.id.Tab3:
                        PerfilFragment perfilFragment = new PerfilFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerPrincipal, perfilFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;

                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void ObtenerUsuario()
    {
        //OBTENER USUARIO ACTUAL

        SharedPreferences sharedPref = getSharedPreferences("LoginPreferences",Context.MODE_PRIVATE);
        vUsuario = sharedPref.getString("spUser","null");
        vContraseña = sharedPref.getString("spPassword","null");
        vTipo = sharedPref.getString("spTipo","null");
    }

}
