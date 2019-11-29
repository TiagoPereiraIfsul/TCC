package com.example.login.Acoes.Barbeiro;


import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.Conexao;
import com.example.login.Lista.Clientes.FragmentListClienteBarbeiro;
import com.example.login.Login.Cadastro.Cadastro;
import com.example.login.Modelo.ModeloCadastro;
import com.example.login.Modelo.ModeloPerfil;
import com.example.login.Modelo.ModeloPerfilEdit;
import com.example.login.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBarbeiroEdit extends Fragment implements View.OnClickListener{
    CardView aliasSalvar, aliasSalvar1;
    Button foto1, foto2, foto3;
    ImageView imagem1, imagem2, imagem3;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView mButtonChooseImage;
    private FirebaseAuth auth;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private EditText aliasnome;
    private StorageTask mUploadTask;

    private int IMAGEM_ID = -1;

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        //criam um stream para ByteArray
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream); //comprime a imagem
        return outputStream.toByteArray(); //retorna a imagem como um Array de Bytes (byte[])
    }



    public FragmentBarbeiroEdit() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_barbeiro_edit, container, false);
        View profileView = inflater.inflate(R.layout.fragment_fragment_barbeiro_edit, null);



        aliasSalvar = (CardView) view.findViewById(R.id.cardView);
        //aliasSalvar1 = (CardView) view.findViewById(R.id.cardView1);

        aliasnome = (EditText) view.findViewById(R.id.aliasnome);
        //foto1 = (Button) view.findViewById(R.id.button);
        //foto2 = (Button) view.findViewById(R.id.button2);
        //foto3 = (Button) view.findViewById(R.id.button3);
        imagem1 = (ImageView) view.findViewById(R.id.imagem1);
        imagem2 = (ImageView) view.findViewById(R.id.imagem2);
        imagem3 = (ImageView) view.findViewById(R.id.imagem3);
        mButtonChooseImage = (ImageView) view.findViewById(R.id.mButtonChooseImage);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users");

        auth = auth.getInstance();

        imagem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IMAGEM_ID = 1;
                openFileChooser();
            }
        });

        imagem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IMAGEM_ID = 2;
                openFileChooser();
            }
        });

        imagem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IMAGEM_ID = 3;
                openFileChooser();
            }
        });

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IMAGEM_ID = 4;
                openFileChooser();
            }
        });



        aliasSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome = aliasnome.getText().toString().trim();
                editarperfil(nome);


            }
        });



        mDatabaseRef.child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.getValue().toString());
                ModeloCadastro m = dataSnapshot.getValue(ModeloCadastro.class);
                //ModeloCadastro mm = dataSnapshot.getValue(ModeloCadastro.class);
                System.out.println(m);
               // System.out.println(mm);

                //pefik
                Picasso.with(getContext()).load(m.getFotoperfil()).into(mButtonChooseImage);

                Picasso.with(getContext()).load(m.getFoto1()).into(imagem1);
                Picasso.with(getContext()).load(m.getFoto2()).into(imagem2);
                Picasso.with(getContext()).load(m.getFoto3()).into(imagem3);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            switch (IMAGEM_ID) {
                case 1:
                    Picasso.with(getContext()).load(mImageUri).into(imagem1);
                    break;
                case 2:
                    Picasso.with(getContext()).load(mImageUri).into(imagem2);
                    break;
                case 3:
                    Picasso.with(getContext()).load(mImageUri).into(imagem3);
                    break;
                case 4:
                    Picasso.with(getContext()).load(mImageUri).into(mButtonChooseImage);

                default://se der merda

            }
        }

    }

        private String getFileExtension(Uri uri) {
       ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 0);

    }



    private void editarperfil(final String nome) {



        mDatabaseRef.child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                              @Override
                                                                                              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                  ModeloCadastro m = dataSnapshot.getValue(ModeloCadastro.class);
                                                                                                  //ModeloPerfilEdit p = dataSnapshot.getValue(ModeloPerfilEdit.class);
                                                                                                  //FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                                                                  //DatabaseReference user = database.getReference("users").child(auth.getUid());

                                                                                                  // ModeloCadastro mm = dataSnapshot.getValue(ModeloCadastro.class);

                                                                                                  m.setNome(nome);
                                                                                                  mDatabaseRef.child(auth.getCurrentUser().getUid()).setValue(m);
                                                                                              }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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


                                    mDatabaseRef.child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            ModeloCadastro m = dataSnapshot.getValue(ModeloCadastro.class);
                                            //ModeloPerfilEdit p = dataSnapshot.getValue(ModeloPerfilEdit.class);
                                            //FirebaseDatabase database = FirebaseDatabase.getInstance();
                                            //DatabaseReference user = database.getReference("users").child(auth.getUid());

                                            // ModeloCadastro mm = dataSnapshot.getValue(ModeloCadastro.class);

                                           // m.setNome(nome);
                                            //user.setValue(p);
                                            //ModeloCadastro m = dataSnapshot.getValue(ModeloCadastro.class);
                                            m.setNome(nome);

                                            switch (IMAGEM_ID) {
                                                case 1:
                                                    m.setFoto1(url);
                                                    break;
                                                case 2:
                                                    m.setFoto2(url);
                                                    break;
                                                case 3:
                                                   m.setFoto3(url);
                                                    break;
                                                case 4:
                                                    m.setFotoperfil(url);
                                                    break;

                                                default://se der merda

                                            }

                                            mDatabaseRef.child(auth.getCurrentUser().getUid()).setValue(m);
                                            //]mDatabaseRef.child(auth.getCurrentUser().getUid()).setValue(mm);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Toast.makeText(FragmentBarbeiroEdit.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getContext(), "Salvo com sucesso", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        auth= Conexao.getFirebaseAuth();
    }


    }
