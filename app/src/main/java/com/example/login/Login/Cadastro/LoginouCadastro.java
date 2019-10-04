package com.example.login.Login.Cadastro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.login.Login.Cadastro.Cadastro;
import com.example.login.Login.Cadastro.MainActivity;
import com.example.login.R;

public class LoginouCadastro extends AppCompatActivity {

    CardView aliaslogin, aliascadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginou_cadastro);

        aliaslogin = (CardView) findViewById(R.id.cardView);
        aliascadastro = (CardView) findViewById(R.id.cardView2);

        aliaslogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        aliascadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Cadastro.class);
                startActivity(intent);
            }
        });
    }
}
