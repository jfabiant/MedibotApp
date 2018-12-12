package pe.edu.tecsup.jfabiant.medibotoriginalapp.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import pe.edu.tecsup.jfabiant.medibotoriginalapp.R;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.fragments.ConfiguracionFragment;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.fragments.HistorialFragment;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.fragments.InformacionFragment;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.fragments.WatsonFragment;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.util.NetworkUtils;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setItemIconTintList(null);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HistorialFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_historial:
                            selectFragment = new HistorialFragment();
                            break;
                        case R.id.nav_watson:
                            selectFragment = new WatsonFragment();
                            break;
                        case R.id.nav_informacion:
                            selectFragment = new InformacionFragment();
                            break;
                        case R.id.nav_configuracion:
                            selectFragment = new ConfiguracionFragment();
                            break;
                            default:
                                selectFragment = null;
                                break;
                    }

                    NetworkUtils utils = new NetworkUtils();

                    if (!NetworkUtils.isConnected(DashboardActivity.this)) {
                        new AlertDialog.Builder(DashboardActivity.this).setIcon(R.drawable.ic_error_outline_black_24dp).setTitle("Sin conexión a internet").setMessage("Por favor conectese a la red").create().show();
                        return false;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectFragment).commit();
                    return true;
                }
            };
}
