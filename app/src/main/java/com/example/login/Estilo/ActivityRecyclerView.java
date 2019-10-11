package com.example.login.Estilo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.login.Modelo.ModeloEstilo;
import com.example.login.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityRecyclerView extends Fragment {
    private RecyclerView recyclerView;
    private List<ModeloEstilo> estilos;
    private EstiloAdapter adapter;
    private ModeloEstilo modeloEstilo;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference mStorage;


    public ActivityRecyclerView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        recyclerView = (RecyclerView) recyclerView.findViewById(R.id.recyclerView);
        modeloEstilo = new ModeloEstilo();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter = new EstiloAdapter(getContext(), estilos, onClickModeloEstilo()));
        mStorage = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        View view = inflater.inflate(R.layout.fragment_activity_recycler_view, container, false);


        return view;
    }

    public Fragment fragment() {
        return this;
    }

    private EstiloAdapter.ModeloEstiloOnClickListener onClickModeloEstilo() {
        final Intent intent = new Intent(getContext(), ModeloEstilo.class);
        return new EstiloAdapter.ModeloEstiloOnClickListener() {
            @Override
            public void onClickModeloEstilo(EstiloAdapter.ModeloEstilosViewHolder holder, int idx) {
                ModeloEstilo p = estilos.get(idx);
                // Intent nova= new Intent(this, Editar_Excluir.class);
                intent.putExtra("Objeto", p); //putextraserializable
                startActivity(intent);
            }
        };
    }

    public void carregarRecyclerView(List<ModeloEstilo> estilos) {
        //cria um objeto da classe ListAdapter, um adaptador List -> ListView
        //associa o adaptador a ListView
        //DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        recyclerView.setAdapter(adapter = new EstiloAdapter(getContext(), estilos, onClickModeloEstilo()));
    }

    @Override
    public void onStart() {
        super.onStart();
       // carregarRecyclerView();
    }



}
