package com.example.login.Login.Cadastro;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.Acoes.Barbeiro.FragmentInicial;
import com.example.login.Acoes.Barbeiro.InicialBarbeiro;
import com.example.login.Conexao;
import com.example.login.Modelo.ModeloCadastro;
import com.example.login.R;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;

public class Cadastro extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    //private ImageView aliasFotoPerfil;
    private CircleImageView aliasFotoPerfil;
    private Button mButtonChooseImage;
    private byte[] imagem = null;
    private EditText aliasNome, aliasSobrenome, aliasEmail, aliasSenha, aliasDescricao;
    private CardView aliasFinalizar;
    private ImageView aliasFace;
    private FirebaseAuth auth;
    private  Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private TextView mTextViewShowUploads;
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;


    private StorageTask mUploadTask;

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        //criam um stream para ByteArray
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream); //comprime a imagem
        return outputStream.toByteArray(); //retorna a imagem como um Array de Bytes (byte[])
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        aliasFotoPerfil = (CircleImageView) findViewById(R.id.imageView);
        mButtonChooseImage = (Button) findViewById(R.id.button_choose_image);
        aliasNome = (EditText) findViewById(R.id.editText);
        //aliasSobrenome = (EditText) findViewById(R.id.editText3);
        aliasEmail = (EditText) findViewById(R.id.editText4);
        aliasSenha = (EditText) findViewById(R.id.editText5);
        //aliasDescricao = (EditText) findViewById(R.id.editText6);
        aliasFinalizar = (CardView) findViewById(R.id.cardView);
        aliasFace = findViewById(R.id.imageView2);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });


        /*aliasFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), InicialBarbeiro.class);
                startActivity(intent);
            }
        });*/

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



        aliasFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String fotoperfl = aliasFotoPerfil;
                String email = aliasEmail.getText().toString().trim();
                String senha = aliasSenha.getText().toString().trim();
                String nome = aliasNome.getText().toString().trim();
                //String sobrenome = aliasSobrenome.getText().toString().trim();
                //String descricao = aliasDescricao.getText().toString().trim();

                //criarUsuario( email, senha, nome, sobrenome, descricao);
                criarUsuario( email, senha, nome);
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(aliasFotoPerfil);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    /*private void openImagesActivity() {
        Intent intent = new Intent(this, ImagesActivity.class);
        startActivity(intent);
    }*/

    //private void criarUsuario(final String email, final String senha, final String nome, final String sobrenome, final String descricao) {
        private void criarUsuario(final String email, final String senha, final String nome) {


            //carega imagem
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                  //  mProgressBar.setProgress(0);
                                }
                            }, 500);

                            //Toast.makeText(Cadastro.this, "Upload successful", Toast.LENGTH_LONG).show();
                            //Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),

                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    final String url = uri.toString();

                                    /// salva o resto

                                    auth.createUserWithEmailAndPassword(email, senha)
                                            .addOnCompleteListener(Cadastro.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if(task.isSuccessful()){
                                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                        DatabaseReference user = database.getReference("users").child(auth.getUid());

                                                        ModeloCadastro m = new ModeloCadastro();

                                                        m.set_id(user.getKey());
                                                        m.setNome(nome);
                                                        m.setFotoperfil(url);
                                                        //m.setDescricao(descricao);
                                                        m.setEmail(email);
                                                        m.setSenha(senha);
                                                        //m.setSobrenome(sobrenome);
                                                        user.setValue(m);

//                                                user.child("nome").setValue(nome);
//                                                user.child("sobrenome").setValue(sobrenome);
//                                                user.child("descricao").setValue(descricao);

                                                        Toast.makeText(Cadastro.this, "Cadastrado com Sucesso", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(Cadastro.this, InicialBarbeiro.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }else
                                                    {
                                                        Toast.makeText(Cadastro.this, "Senha deve conter pelo menos 5 letras e 5 números", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });



                                }
                            });




                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Cadastro.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            //mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    protected void onStart() {
        super.onStart();
        auth= Conexao.getFirebaseAuth();
    }

    }

