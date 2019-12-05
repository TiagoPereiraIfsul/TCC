package com.example.login.Lista.Clientes;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.login.Estilo.EstiloAdapter;
import com.example.login.Modelo.ModeloCliente;
import com.example.login.Modelo.ModeloEstilo;
import com.example.login.R;
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
    private List<ModeloCliente> modeloClientes;
    private ClienteAdapter adapter;
    ModeloCliente modeloCliente;
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
        modeloCliente = new ModeloCliente();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter = new ClienteAdapter(getContext(), modeloClientes, null));

        FirebaseDatabase.getInstance().getReference("HorariosMarcados").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ModeloCliente> lista = new ArrayList<>();

                for(DataSnapshot d : dataSnapshot.getChildren()) {
                    ModeloCliente m = d.getValue(ModeloCliente.class);
//                    if(m.getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                        if(m.getHorario().contains("Manhã"))
                        lista.add(m);
                }

                //lista = (List<ModeloCliente>) lista.stream().filter(e->e.getHorario().equals("")).collect(Collectors.toList());
                System.out.println(lista);
                //modeloCliente = lista;
                carregarRecyclerView(lista);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }



    private ClienteAdapter.ModeloClienteOnClickListener onClickModeloCliente() {
        final Intent intent = new Intent(getContext(), ModeloEstilo.class);
        return new ClienteAdapter.ModeloClienteOnClickListener() {
            @Override
            public void onClickModeloCliente(ClienteAdapter.ModeloClientesViewHolder holder, int idx) {
                ModeloCliente p = modeloClientes.get(idx);

            }
        };
    }

    public void carregarRecyclerView(List<ModeloCliente> modeloClientes) {
        recyclerView.setAdapter(adapter = new ClienteAdapter(getContext(), modeloClientes, onClickModeloCliente()));


    }

}
