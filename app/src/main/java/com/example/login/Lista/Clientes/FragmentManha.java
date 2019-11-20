package com.example.login.Lista.Clientes;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.login.Estilo.EstiloAdapter;
import com.example.login.Estilo.FragmentAddEstilo;
import com.example.login.Modelo.ModeloClientes;
import com.example.login.Modelo.ModeloEstilo;
import com.example.login.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentManha extends Fragment {
    private RecyclerView recyclerView;
    private List<ModeloEstilo> modeloEstilos;
    private EstiloAdapter adapter;
    ModeloClientes modeloCliente;
    private Button excluir;


    public FragmentManha() {
        // Required empty public constructor
    }


    @Override
    @SuppressWarnings("newApi")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //teste
        View view = inflater.inflate(R.layout.fragment_activity_recycler_view2, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        modeloCliente = new ModeloClientes();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter = new EstiloAdapter(getContext(), modeloEstilos, null));

        FirebaseDatabase.getInstance().getReference("HorariosMarcados").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ModeloEstilo> lista = new ArrayList<>();

                for(DataSnapshot d : dataSnapshot.getChildren()) {
                    ModeloEstilo m = d.getValue(ModeloEstilo.class);
//                    if(m.getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                        lista.add(m);
                }

                //lista = (List<ModeloEstilo>) lista.stream().filter(e->e.getNome().equals("HorarioMarcado")).collect(Collectors.toList());
                System.out.println(lista);
                modeloEstilos = lista;
                carregarRecyclerView(lista);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }



    public void carregarRecyclerView(List<ModeloEstilo> modeloClientes) {
        recyclerView.setAdapter(adapter = new EstiloAdapter(getContext(), modeloClientes, null));

    }

}
