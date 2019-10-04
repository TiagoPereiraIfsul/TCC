package com.example.login.Login.Cadastro;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.Acoes.Barbeiro.InicialBarbeiro;
import com.example.login.Conexao;
import com.example.login.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ImageView  aliasTwitter, aliasInsta, aliasFace;
    EditText aliasEmail, aliasSenha;
    TextView aliasCadastro;
    CardView aliasLogin;
    ProgressBar progressBar;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aliasLogin = findViewById(R.id.cardView);
        aliasTwitter = findViewById(R.id.imageView4);
        aliasInsta = findViewById(R.id.imageView3);
        aliasFace = findViewById(R.id.imageView2);
        aliasEmail = findViewById(R.id.editText);
        aliasSenha = findViewById(R.id.editText2);
        aliasCadastro = findViewById(R.id.textView2);

        progressBar = (ProgressBar) findViewById(R.id.progressBarLogin);
        progressBar.setVisibility(View.INVISIBLE);

        aliasLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = aliasEmail.getText().toString().trim();
                String senha = aliasSenha.getText().toString().trim();
                login(email, senha);
            }
        });

        aliasCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Cadastro.class);
                startActivity(intent);
            }
        });

        aliasTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://twitter.com/login"));
                startActivity(intent);
            }
        });

        aliasInsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.instagram.com/"));
                startActivity(intent);
            }
        });

        aliasFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://pt-br.facebook.com/"));
                startActivity(intent);
            }
        });


    }

    private void login(String email, String senha) {
        progressBar.setVisibility(View.VISIBLE);
        auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent i=new Intent(MainActivity.this, InicialBarbeiro.class);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }


    @Override
    protected void onStart() {
        super.onStart();
        auth= Conexao.getFirebaseAuth();
    }


    private Context getContext(){return this;}
    private void alert(String s)
    {
        Toast.makeText(this,s, Toast.LENGTH_SHORT).show();
    }

}
