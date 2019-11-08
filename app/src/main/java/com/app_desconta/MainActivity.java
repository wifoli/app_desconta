package com.app_desconta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.app_desconta.login.Tela_login;
import com.app_desconta.ui.main.SectionsPagerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import static com.app_desconta.util.Sair.sair;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private TextView nomeSobrenome;
    private TextView email;
    private Toolbar toolbar;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setarDrawer();
        setarTabs();
        setarNomeeEmailNoDrawer();

        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.dn_perfil:
                startActivity(new Intent(this, PerfilActivity.class));
                break;
            case R.id.dn_duvidas:
                Log.d("Teste", "item 2");
                break;

            case R.id.dn_comentarios:
                Log.d("Teste", "item 3");
                break;

            case R.id.dn_sair:
                sair();
                this.finishAffinity();
                startActivity(new Intent(this, Tela_login.class));
                break;

            default:
                Log.d("Teste", "nao entrou em nehum item");
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setarDrawer(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setarNomeeEmailNoDrawer(){
        View headerView = navigationView.getHeaderView(0);
        nomeSobrenome = (TextView) headerView.findViewById(R.id.nav_header_textView);
        email = (TextView) headerView.findViewById(R.id.nav_header_textView_email);
        nomeSobrenome.setText(Usuario.getInsance().getUsuario().getPessoa().getNome() + " " + Usuario.getInsance().getUsuario().getPessoa().getSobrenome());
        email.setText(Usuario.getInsance().getUsuario().getEmail());
    }

    private void setarTabs(){
        ViewPager viewPager = findViewById(R.id.view_pager);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }


 }
