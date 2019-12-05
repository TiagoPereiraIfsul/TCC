package com.example.login.Lista.Clientes;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.login.Modelo.ModeloCliente;
import com.example.login.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityRecyclerView2 extends Fragment {
    private RecyclerView recyclerView;
    private List<ModeloCliente> clientes;
    private ClienteAdapter adapter;
    private ModeloCliente modeloClientes;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference mStorage;


    public ActivityRecyclerView2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // Inflate the layout for this fragment
        recyclerView = (RecyclerView) recyclerView.findViewById(R.id.recyclerView);
        modeloClientes = new ModeloCliente();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter = new ClienteAdapter(getContext(), clientes, onClickModeloCliente()));
        mStorage = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();


        View view = inflater.inflate(R.layout.fragment_activity_recycler_view2, container, false);

        return view;
    }

    public Fragment fragment() {
        return this;
    }

    private ClienteAdapter.ModeloClienteOnClickListener onClickModeloCliente() {
        final Intent intent = new Intent(getContext(), ModeloCliente.class);
        return new ClienteAdapter.ModeloClienteOnClickListener() {
            @Override
            public void onClickModeloCliente(ClienteAdapter.ModeloClientesViewHolder holder, int idx) {
                ModeloCliente p = clientes.get(idx);
                // Intent nova= new Intent(this, Editar_Excluir.class);
                intent.putExtra("Objeto", p); //putextraserializable
                startActivity(intent);
            }
        };
    }

    public void carregarRecyclerView(List<ModeloCliente> clientes) {
        //cria um objeto da classe ListAdapter, um adaptador List -> ListView
        //associa o adaptador a ListView
        //DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        recyclerView.setAdapter(adapter = new ClienteAdapter(getContext(), clientes, onClickModeloCliente()));
    }

    @Override
    public void onStart() {
        super.onStart();
        // carregarRecyclerView();
    }

}
