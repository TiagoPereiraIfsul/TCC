package com.example.login.Acoes.Barbeiro;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.login.Estilo.FragmentAddEstilo;
import com.example.login.Lista.Clientes.FragmentListClienteBarbeiro;
import com.example.login.Modelo.ModeloCadastro;
import com.example.login.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class InicialBarbeiro extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //private ImageView foto;
    private CircleImageView foto;



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial_barbeiro);

        getSupportFragmentManager().beginTransaction().replace(R.id.conteiner, new FragmentInicial()).commit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         final FloatingActionButton fab = findViewById(R.id.fab);
       fab.setOnClickListener(new View.OnClickListener() {

           @Override
          public void onClick(View view) {
              fab.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent intent = new Intent(v.getContext(), InicialBarbeiro.class);
                       startActivity(intent);
                   }
               });
        }
       });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        final TextView aliasNome;
        aliasNome = (TextView) findViewById(R.id.textView8);
        //foto = (ImageView) findViewById(R.id.aliasfoto);
        foto = (CircleImageView) findViewById(R.id.aliasfoto);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference uidRef = rootRef.child("users").child(uid);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ModeloCadastro modeloCadastro = dataSnapshot.getValue(ModeloCadastro.class);
                String nome = modeloCadastro.getNome();
                String sobrenome = modeloCadastro.getSobrenome();
                aliasNome.setText(sobrenome);
                aliasNome.setText(nome);
                Log.d("BARBEIRO", nome  + " / " + sobrenome);

                Picasso.with(getBaseContext()).load(modeloCadastro.getFotoperfil()).into(foto);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        uidRef.addListenerForSingleValueEvent(valueEventListener);
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            FragmentInicial fragmentInicial = new FragmentInicial();
            ft.replace(R.id.conteiner, fragmentInicial);
            ft.commit();


        } else if (id == R.id.nav_gallery) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            FragmentPerfilBarbeiro fragmentPerfilBarbeiro = new FragmentPerfilBarbeiro();
            ft.replace(R.id.conteiner, fragmentPerfilBarbeiro);
            ft.commit();


        } else if (id == R.id.nav_slideshow) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            FragmentListClienteBarbeiro fragmentListClienteBarbeiro = new FragmentListClienteBarbeiro();
            ft.replace(R.id.conteiner, fragmentListClienteBarbeiro);
            ft.commit();

        } else if (id == R.id.nav_tools) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            FragmentAddEstilo fragmentAddEstilo = new FragmentAddEstilo();
            ft.replace(R.id.conteiner, fragmentAddEstilo);
            ft.commit();


        } else if (id == R.id.nav_share) {
            finish();


        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
