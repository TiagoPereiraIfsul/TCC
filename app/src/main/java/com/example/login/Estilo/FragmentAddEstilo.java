package com.example.login.Estilo;


import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.login.Acoes.Barbeiro.InicialBarbeiro;
import com.example.login.Lista.Clientes.ViewPagerAdapter;
import com.example.login.Login.Cadastro.Cadastro;
import com.example.login.Modelo.ModeloCadastro;
import com.example.login.Modelo.ModeloEstilo;
import com.example.login.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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

import java.lang.reflect.Array;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAddEstilo extends Fragment implements View.OnClickListener{
    private static final int PICK_IMAGE_REQUEST = 1;

    private TabLayout tabLayout2;
    private ViewPager viewPager2;
    private ViewPagerAdapter2 viewPagerAdapter2;
    private String [] add = new String[] {"Cabelo - R$ 25,00", "Barba - R$ 10,00", "Sobrancelha - R$ 5,00"};
    private Spinner combobox;
    private EditText nome;
    private ModeloEstilo estilo;
    private CardView salvar, versalvos, button;
    private FirebaseAuth auth;
    private  Uri mImageUri;
    private StorageTask mUploadTask;
    private StorageReference mStorageRef;
    private int IMAGEM_ID = -1;
    private DatabaseReference mDatabaseRef;
    private ImageView foto;



    public FragmentAddEstilo() {
        // Required empty public constructor
    }

    private void setupViewPager2(ViewPager viewPager2) {
        ViewPagerAdapter2 adapter2 = new ViewPagerAdapter2(getActivity().getSupportFragmentManager());
        adapter2.addFragment(new FragmentAddEstilo(), "ADICIONAR");
        adapter2.addFragment(new FragmentCabelo(), "CABELO");
        adapter2.addFragment(new FragmentBarba(), "BARBA");
        adapter2.addFragment(new FragmentSobrancelha(), "SOBRANCELHA");
        viewPager2.setAdapter(adapter2);
    }

    /*@Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 0);

    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_add_estilo, container, false);

        viewPagerAdapter2 = new ViewPagerAdapter2(getActivity().getSupportFragmentManager());
        viewPager2 = (ViewPager) view.findViewById(R.id.viewpager2);
//        setupViewPager2(viewPager2);
        //tabLayout2 = (TabLayout) view.findViewById(R.id.tabs2);
        //tabLayout2.setupWithViewPager(viewPager2);
        combobox = (Spinner) view.findViewById(R.id.spinner);
        //combobox2 = (Spinner) view.findViewById(R.id.spinner2);
        nome = (EditText) view.findViewById(R.id.editText7);
        salvar = (CardView) view.findViewById(R.id.cardView2);
        versalvos = (CardView) view.findViewById(R.id.cardView3);
        button = (CardView) view.findViewById(R.id.button);
        foto = (ImageView) view.findViewById(R.id.imageView);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users");

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            ModeloEstilo modeloEstilo = (ModeloEstilo) bundle.getSerializable("objeto");
            if(modeloEstilo != null)
            {
                nome.setText(modeloEstilo.getNome());
            }
        }


        estilo = new ModeloEstilo();

        ArrayAdapter<String> adaptador =
               new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, add);
      combobox.setAdapter(adaptador);




        versalvos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                FragmentListEstilos fragmentListEstilos = new FragmentListEstilos();
                ft.replace(R.id.conteiner, fragmentListEstilos);
                ft.commit();

                //editarperfil();


            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IMAGEM_ID = 1;
                openFileChooser();
            }
        });

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verificao

                if(nome.getText().toString().isEmpty()) {
                    Toast.makeText(v.getContext(), "Não pode ser salvo, campo vazio!", Toast.LENGTH_SHORT).show();
                    return;
                }

                editarperfil();


                Toast.makeText(getContext(), "Salvo com sucesso!", Toast.LENGTH_SHORT).show();


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
                    Picasso.with(getContext()).load(mImageUri).into(foto);
                    break;


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

    private void editarperfil() {



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

                                    //estilo.setNome(nome.getText().toString());
                                    estilo.setNome(nome.getText().toString());
                                    estilo.setBarbeiro(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    //estilo.setCabelo(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    //estilo.setBarba(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    //estilo.setSobrancelha(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    estilo.setCategoria((String) combobox.getSelectedItem());
                                    estilo.setId(FirebaseDatabase.getInstance().getReference().push().getKey());
    estilo.setFoto(url);


                                    FirebaseDatabase.getInstance().getReference("Estilos")
                                            .child(estilo.getId()).setValue(estilo);



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
            //Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }


    }









}
