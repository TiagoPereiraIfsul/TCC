package com.example.login.Acoes.Barbeiro;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.login.Login.Cadastro.Cadastro;
import com.example.login.Modelo.ModeloCadastro;
import com.example.login.Modelo.ModeloPerfil;
import com.example.login.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPerfilBarbeiro extends Fragment implements View.OnClickListener{
    private CardView aliasEdit;
    //private ImageView aliasfotoperfil;
    private CircleImageView aliasfotoperfil;
    private ImageView aliasFoto1;
    private ImageView aliasFoto2;
    private ImageView aliasFoto3;
    private FirebaseAuth auth;
    private ModeloCadastro modeloCadastro = new ModeloCadastro();
    private ModeloPerfil modeloPerfil = new ModeloPerfil();
    private FragmentBarbeiroEdit fragmentBarbeiroEdit = new FragmentBarbeiroEdit();
    private ValueEventListener valueEventListener;
    private TextView aliasNome, aliasDescricao, aliasSobrenome;
    private StorageReference mStorage;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    private  static  final int PICK_IMAGE_REQUEST=123;
    private byte[] imagem = null;

    public static byte [] getBitmapAsbyteArray(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,outputStream);
        return outputStream.toByteArray();
    }


    public FragmentPerfilBarbeiro() {
        //verificaparametro();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_perfil_barbeiro, container, false);

        mStorage = FirebaseStorage.getInstance().getReference();

        aliasEdit = (CardView) view.findViewById(R.id.cardView);
        aliasNome = (TextView) view.findViewById(R.id.alianome);
        //aliasSobrenome = (TextView) view.findViewById(R.id.aliasobrenome);
        aliasfotoperfil = (CircleImageView) view.findViewById(R.id.aliasfotoperfil);
        //aliasDescricao = (TextView) view.findViewById(R.id.aliasdescricao);

        aliasFoto1 = (ImageView) view.findViewById(R.id.imagem1);
        //aliasFoto1.setOnClickListener(this);

        aliasFoto2 = (ImageView) view.findViewById(R.id.aliasfoto2);
        //aliasFoto2.setOnClickListener(this);

        aliasFoto3 = (ImageView) view.findViewById(R.id.aliasfoto3);
        //aliasFoto3.setOnClickListener(this);


        auth = FirebaseAuth.getInstance();

        modeloCadastro = new ModeloCadastro();
        modeloPerfil = new ModeloPerfil();
        fragmentBarbeiroEdit = new FragmentBarbeiroEdit();



        View profileView = inflater.inflate(R.layout.fragment_fragment_perfil_barbeiro, null);



        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference uidRef = rootRef.child("users").child(uid);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ModeloCadastro modeloCadastro = dataSnapshot.getValue(ModeloCadastro.class);
                ModeloPerfil modeloPerfil = dataSnapshot.getValue(ModeloPerfil.class);
                String nome = modeloCadastro.getNome();
                aliasNome.setText(nome);
                //String descricao = modeloCadastro.getDescricao();
                //aliasDescricao.setText(descricao);
                //String sobrenome = modeloCadastro.getSobrenome();
                //aliasSobrenome.setText(sobrenome);
                //byte [] fotoperfil = modeloCadastro.getFotoperfil();
                //aliasfotoperfil.setImageBitmap(fotoperfil);

//                aliasfotoperfil =
                Picasso.with(getContext()).load(modeloCadastro.getFotoperfil()).into(aliasfotoperfil);
                //modeloCadastro.getFotoperfil()
                if(modeloCadastro.getFoto1() != null){
                    Picasso.with(getContext()).load(modeloPerfil.getFoto1()).into(aliasFoto1);}

                if(modeloCadastro.getFoto1() != null){
                    Picasso.with(getContext()).load(modeloPerfil.getFoto2()).into(aliasFoto2);}

                if(modeloCadastro.getFoto1() != null){
                    Picasso.with(getContext()).load(modeloPerfil.getFoto3()).into(aliasFoto3);}

                //Log.d("BARBEIRO", nome + " / " +  sobrenome);
                Log.d("BARBEIRO", nome );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        uidRef.addListenerForSingleValueEvent(valueEventListener);



        aliasEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                FragmentBarbeiroEdit fragmentEdit = new FragmentBarbeiroEdit();
                ft.replace(R.id.conteiner, fragmentEdit);
                ft.commit();

            }
        });



        return view;
    }
    public static byte[] getBitmapAsByteArray(Bitmap bitmap1) {
        //criam um stream para ByteArray
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.PNG, 0, outputStream); //comprime a imagem
        return outputStream.toByteArray(); //retorna a imagem como um Array de Bytes (byte[])
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri arquivoUri = data.getData(); //obtém o URI da imagem
            Bitmap bitmap = null; //mapeia a imagem para um objeto bitmap
            try {
                bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(arquivoUri));
                aliasFoto1.setImageURI(arquivoUri); //coloca a imagem no ImageView
                //aliasFoto2.setImageURI(arquivoUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            byte[] img = getBitmapAsByteArray(bitmap); //converte para um fluxo de bytes
            imagem = img; //coloca a imagem no objeto imagem (um array de bytes (byte[]))
        }


    }


}
